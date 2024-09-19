package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class DeliveryAddress(

    @SerializedName("address")
    val address: String,

    @SerializedName("title")
    val title: String
)