package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class Item(

    @SerializedName("product_id")
    val product_id: Int,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("unit_price")
    val unit_price: Int
)