package com.example.loginregisterretrofit.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginregisterretrofit.SubCategoryAdapter
import com.example.loginregisterretrofit.model.datalayer.SubCategoryProductResponse
import com.example.loginregisterretrofit.databinding.FragmentABinding
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentA : Fragment() {

    private lateinit var binding: FragmentABinding
    private val apiService = ApiClient.retrofit.create(ApiService::class.java)
    private lateinit var adapter: SubCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize view binding
        binding = FragmentABinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(subcategoryId: String, subcategoryName: String): FragmentA {
            val fragment = FragmentA()
            val bundle = Bundle()
            bundle.putString("subcategory_id", subcategoryId)
            bundle.putString("subcategory_name", subcategoryName)
            fragment.arguments = bundle
            return fragment
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subcategoryId = arguments?.getString("subcategory_id")
//        val subcategoryName = arguments?.getString("subcategory_name")


//        binding.textViewSubcategoryName.text = subcategoryName

        binding.recyclerSubCatProView.layoutManager = LinearLayoutManager(requireContext())

        // Fetching products based on the subcategory_id (Android, iPhone ....)
        subcategoryId?.let {
            fetchProducts(it)
        }
    }

    private fun fetchProducts(subcategoryId: String) {

        val call = apiService.getSubCategoryProduct(subcategoryId.toInt())

        call.enqueue(object : Callback<SubCategoryProductResponse> {
            override fun onResponse(
                call: Call<SubCategoryProductResponse>,
                response: Response<SubCategoryProductResponse>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(requireContext(), "Failed to fetch products", Toast.LENGTH_SHORT).show()
                    return
                }

                val result = response.body()
                Log.d("SearchResult", "onResponse: $result")

                result?.let {
                    if (it.products.isNotEmpty()) {

                        adapter = SubCategoryAdapter(it.products)
                        binding.recyclerSubCatProView.adapter = adapter
                        adapter.notifyDataSetChanged()

                    } else {
                        Toast.makeText(requireContext(), "No products available", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<SubCategoryProductResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
