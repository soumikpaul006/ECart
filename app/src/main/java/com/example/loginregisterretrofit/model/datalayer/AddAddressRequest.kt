package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class AddAddressRequest(


    @SerializedName("user_id")
    val user_id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("address")
    val address: String,


)