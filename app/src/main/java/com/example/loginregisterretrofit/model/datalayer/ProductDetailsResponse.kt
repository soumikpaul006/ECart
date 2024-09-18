package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(

    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("product")
    val product: ProductX,
)