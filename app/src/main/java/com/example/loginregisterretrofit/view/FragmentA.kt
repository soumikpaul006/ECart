package com.example.loginregisterretrofit.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginregisterretrofit.viewmodel.CartViewModel
import com.example.loginregisterretrofit.R
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
    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: SubCategoryAdapter
    private val apiService = ApiClient.retrofit.create(ApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize view binding
        binding = FragmentABinding.inflate(inflater, container, false)

        // Initialize CartViewModel with activity-scoped ViewModelProvider
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]


        return binding.root
    }

    companion object {

        fun newInstance(subcategoryId: String, subcategoryName: String): FragmentA {
            val fragment = FragmentA()
            val bundle = Bundle().apply {
                putString("subcategory_id", subcategoryId)
                putString("subcategory_name", subcategoryName)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subcategoryId = arguments?.getString("subcategory_id")


        binding.recyclerSubCatProView.layoutManager = LinearLayoutManager(requireContext())


        binding.btnGoToCart.setOnClickListener {
            openCartActivity()
        }


        subcategoryId?.let {
            fetchProducts(it)
        }
    }


    private fun openCartActivity() {
        val intent = Intent(requireContext(), CartActivity::class.java)
        startActivity(intent)
    }

    // Function to fetch products
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

                result?.let {
                    if (it.products.isNotEmpty()) {
                        adapter = SubCategoryAdapter(it.products) { product ->
                            cartViewModel.addToCart(product)  // Add product to cart
                            Toast.makeText(requireContext(), "${product.product_name} added to cart", Toast.LENGTH_SHORT).show()
                        }
                        binding.recyclerSubCatProView.adapter = adapter
                    } else {
                        Toast.makeText(requireContext(), "No products available", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<SubCategoryProductResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}





