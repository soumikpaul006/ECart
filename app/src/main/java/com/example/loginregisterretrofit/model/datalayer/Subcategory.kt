package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class Subcategory(

    @SerializedName("subcategory_id")
    val subcategory_id: String,

    @SerializedName("subcategory_name")
    val subcategory_name: String,


    @SerializedName("category_id")
    val category_id: String,

    @SerializedName("subcategory_image_url")
    val subcategory_image_url: String,

    @SerializedName("is_active")
    val is_active: String,

)