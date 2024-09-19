package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class OrderResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("order_id")
    val order_id: Int,

    @SerializedName("status")
    val status: Int
)