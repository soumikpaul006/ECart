package com.example.loginregisterretrofit.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregisterretrofit.OrderAdapter
import com.example.loginregisterretrofit.PreferenceHelper
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.model.datalayer.UserOrdersResponse
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class OrderActivity : AppCompatActivity() {

    private lateinit var orderAdapter: OrderAdapter
    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)


        preferenceHelper = PreferenceHelper(this)


        userId = preferenceHelper.getUserId() ?: ""


        if (userId.isEmpty()) {
            Toast.makeText(this, "User ID not found. Please log in.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(this)
        orderAdapter = OrderAdapter(emptyList())
        recyclerView.adapter = orderAdapter

        // Fetch and display the user's orders
        getUserOrders(userId.toInt())
    }

    private fun getUserOrders(userId: Int) {

        val apiService=ApiClient.retrofit.create(ApiService::class.java)

        apiService.getUserOrders(userId).enqueue(object : Callback<UserOrdersResponse> {
            override fun onResponse(
                call: Call<UserOrdersResponse>,
                response: Response<UserOrdersResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val orders = response.body()?.orders
                    orderAdapter.setOrders(orders ?: emptyList())
                } else {
                    Log.e("OrderActivity", "Failed to get orders: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserOrdersResponse>, t: Throwable) {
                Log.e("OrderActivity", "API call failed: ${t.message}")
            }
        })
    }
}

