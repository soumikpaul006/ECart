package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class OrderRequest(

    @SerializedName("bill_amount")
    val bill_amount: Int,

    @SerializedName("delivery_address")
    val delivery_address: DeliveryAddress,

    @SerializedName("items")
    val items: List<Item>,

    @SerializedName("payment_method")
    val payment_method: String,

    @SerializedName("user_id")
    val user_id: Int
)