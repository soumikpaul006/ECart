package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class SubCategoryResponse(


    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("subcategories")
    val subcategories: List<Subcategory>
)