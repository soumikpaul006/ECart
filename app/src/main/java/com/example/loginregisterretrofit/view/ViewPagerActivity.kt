package com.example.loginregisterretrofit.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregisterretrofit.ViewPagerAdapter
import com.example.loginregisterretrofit.model.datalayer.SubCategoryResponse
import com.example.loginregisterretrofit.databinding.ActivityViewPagerBinding
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
}