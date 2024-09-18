package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class ProductSearchResponse(

    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("products")
    val products: List<ProductXX>

)