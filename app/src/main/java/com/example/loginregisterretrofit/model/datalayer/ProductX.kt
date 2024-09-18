package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class ProductX(

    @SerializedName("product_id")
    val product_id: String,

    @SerializedName("product_name")
    val product_name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("category_id")
    val category_id: String,

    @SerializedName("sub_category_id")
    val sub_category_id: String,

    @SerializedName("price")
    val price: String,

    @SerializedName("average_rating")
    val average_rating: String,

    @SerializedName("product_image_url")
    val product_image_url: String,

    @SerializedName("is_active")
    val is_active: String,

    @SerializedName("images")
    val images: List<Image>,

    @SerializedName("specifications")
    val specifications: List<Specification>,

    @SerializedName("reviews")
    val reviews: List<Any>,

)