package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName


data class Addresse(

    @SerializedName("address_id")
    val address_id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("address")
    val address: String,
)