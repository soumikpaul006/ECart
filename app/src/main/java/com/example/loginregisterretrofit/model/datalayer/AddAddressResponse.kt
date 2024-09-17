package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class AddAddressResponse(

    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

)