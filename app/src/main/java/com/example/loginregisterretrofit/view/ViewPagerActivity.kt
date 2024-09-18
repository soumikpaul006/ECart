package com.example.loginregisterretrofit.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregisterretrofit.SearchResultsAdapter
import com.example.loginregisterretrofit.ViewPagerAdapter
import com.example.loginregisterretrofit.model.datalayer.SubCategoryResponse
import com.example.loginregisterretrofit.databinding.ActivityViewPagerBinding
import com.example.loginregisterretrofit.model.datalayer.ProductSearchResponse
import com.example.loginregisterretrofit.model.datalayer.ProductXX
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewPagerBinding
    val apiService = ApiClient.retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val subcategoryID = intent.getStringExtra("CATEGORY_ID")

        if (subcategoryID != null) {
            fetchProducts(subcategoryID)
        }


        binding.btnSearch.setOnClickListener {
            val searchText = binding.etSearch.text.toString()
            if (searchText.isNotEmpty()) {
                searchProducts(searchText)
            } else {
                Toast.makeText(this, "Please enter a search query", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun fetchProducts(scid: String) {
        val call = apiService.getSubCategory(scid)

        call.enqueue(object : Callback<SubCategoryResponse> {
            override fun onResponse(
                call: Call<SubCategoryResponse>,
                response: Response<SubCategoryResponse>
            ) {

                if(!response.isSuccessful)
                {
                    Toast.makeText(this@ViewPagerActivity, "Failed to fetch products", Toast.LENGTH_SHORT).show()
                    return
                }


                val result: SubCategoryResponse?=response.body()
                Log.d("SearchResult","onResponse: ${result}")

                //TODO
                //Display the subcategories in the Tab Layout

                result?.let {
                    if (it.subcategories.isNotEmpty()) {

                        val fragments = it.subcategories.map { subcategory ->
                            FragmentA.newInstance(subcategory.subcategory_id, subcategory.subcategory_name)
                        }


                        val adapter = ViewPagerAdapter(fragments, this@ViewPagerActivity)
                        binding.viewPager.adapter = adapter

                        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                            tab.text = it.subcategories[position].subcategory_name
                        }.attach()
                    } else {
                        Toast.makeText(this@ViewPagerActivity, "No subcategories available", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<SubCategoryResponse>, t: Throwable) {
                Toast.makeText(this@ViewPagerActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun searchProducts(query: String) {

        val call = apiService.searchProducts(query)

        call.enqueue(object : Callback<ProductSearchResponse> {
            override fun onResponse(
                call: Call<ProductSearchResponse>,
                response: Response<ProductSearchResponse>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@ViewPagerActivity, "Failed to search products", Toast.LENGTH_SHORT).show()
                    return
                }

                val searchResult: ProductSearchResponse? = response.body()

                searchResult?.let {
                    if (it.products.isNotEmpty()) {

                        displaySearchResults(it.products)
                    } else {
                        Toast.makeText(this@ViewPagerActivity, "No products found", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ProductSearchResponse>, t: Throwable) {
                Toast.makeText(this@ViewPagerActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displaySearchResults(products: List<ProductXX>) {
        val recyclerView = RecyclerView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SearchResultsAdapter(products)
        setContentView(recyclerView)
    }

}