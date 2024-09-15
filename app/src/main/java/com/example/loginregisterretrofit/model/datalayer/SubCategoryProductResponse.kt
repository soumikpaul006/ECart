package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class SubCategoryProductResponse(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("products")
    val products: List<Product>

)