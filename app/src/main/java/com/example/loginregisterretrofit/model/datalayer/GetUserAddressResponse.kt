package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class GetUserAddressResponse(

    @SerializedName("addresses")
    val addresses: List<Addresse>,

    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: Int
)