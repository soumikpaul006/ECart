package com.example.loginregisterretrofit.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.loginregisterretrofit.CategoryAdapter
import com.example.loginregisterretrofit.OnCategoryClickListener
import com.example.loginregisterretrofit.PreferenceHelper
import com.example.loginregisterretrofit.model.datalayer.Category
import com.example.loginregisterretrofit.model.datalayer.ProductCategoryResponse
import com.example.loginregisterretrofit.databinding.ActivityUserPageBinding
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPageActivity : AppCompatActivity(), OnCategoryClickListener {

    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var binding: ActivityUserPageBinding
    val apiService= ApiClient.retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this,2)

        fetchCategories()


        preferenceHelper = PreferenceHelper(this)

        binding.btnLogout.setOnClickListener{
            preferenceHelper.clearUserSession()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }

    }

    fun fetchCategories()
    {
        val call: Call<ProductCategoryResponse> = apiService.getProductCategory()

        call.enqueue(object: Callback<ProductCategoryResponse> {
            override fun onResponse(
                call: Call<ProductCategoryResponse>,
                response: Response<ProductCategoryResponse>
            ) {
                if(!response.isSuccessful)
                {
                    Toast.makeText(this@UserPageActivity,"Failed to get category", Toast.LENGTH_SHORT).show()
                    return
                }

                val searchResult: ProductCategoryResponse?=response.body()
                Log.d("SearchResult","onResponse: ${searchResult}")

                searchResult?.let {
                    binding.recyclerView.adapter= CategoryAdapter(it.categories, this@UserPageActivity)
                }
            }

            override fun onFailure(call: Call<ProductCategoryResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@UserPageActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onCategoryClick(category: Category) {
        // Navigate to ProductActivity with the selected category
        val intent = Intent(this, ViewPagerActivity::class.java)
        intent.putExtra("CATEGORY_ID", category.category_id)
        startActivity(intent)
    }
}