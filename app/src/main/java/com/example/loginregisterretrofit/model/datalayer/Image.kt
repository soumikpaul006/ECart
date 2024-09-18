package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("image")
    val image: String,

    @SerializedName("display_order")
    val display_order: String
)