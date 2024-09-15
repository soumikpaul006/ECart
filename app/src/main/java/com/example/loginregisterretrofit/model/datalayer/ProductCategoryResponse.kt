package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class ProductCategoryResponse(

    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: Int,

    @SerializedName("categories")
    val categories: List<Category>
)