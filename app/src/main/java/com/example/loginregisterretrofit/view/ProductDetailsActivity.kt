package com.example.loginregisterretrofit.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.SpecificationAdapter
import com.example.loginregisterretrofit.databinding.ActivityProductDetailsBinding
import com.example.loginregisterretrofit.model.datalayer.ProductDetailsResponse
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        apiService = ApiClient.retrofit.create(ApiService::class.java)


        val productId = intent.getStringExtra("product_id") ?: ""

        // Fetch product details from the API
        if (productId.isNotEmpty()) {
            fetchProductDetails(productId)
        } else {
            Toast.makeText(this, "Invalid product ID", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchProductDetails(productId: String) {

        val call=apiService.getProductDetails(productId)

        call.enqueue(object : Callback<ProductDetailsResponse>{
            override fun onResponse(
                call: Call<ProductDetailsResponse>,
                response: Response<ProductDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        displayProductDetails(it)
                    }
                } else {
                    Toast.makeText(this@ProductDetailsActivity, "Failed to load product details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductDetailsResponse>, t: Throwable) {
                Toast.makeText(this@ProductDetailsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun displayProductDetails(productDetails: ProductDetailsResponse) {


        val product = productDetails.product


        binding.txtProductName.text = product.product_name
        binding.txtProductDescription.text = product.description
        binding.txtProductPrice.text = "Price: $${product.price}"
        binding.txtProductRating.rating = product.average_rating.toFloat()


        if (product.images.isNotEmpty()) {
            Glide.with(this)
                .load("https://apolisrises.co.in/myshop/images/${product.images[0].image}")
                .into(binding.imgProduct)
        }


        val specificationAdapter = SpecificationAdapter(product.specifications)
        binding.recyclerViewSpecifications.adapter = specificationAdapter
        binding.recyclerViewSpecifications.layoutManager = LinearLayoutManager(this)
    }

}