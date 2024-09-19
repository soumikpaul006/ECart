package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class UserOrdersResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("orders")
    val orders: List<Order>,

    @SerializedName("status")
    val status: Int
)