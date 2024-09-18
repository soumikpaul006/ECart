package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class Specification(

    @SerializedName("specification_id")
    val specification_id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("specification")
    val specification: String,

    @SerializedName("display_order")
    val display_order: String
)