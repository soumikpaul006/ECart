package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class AddUserResponse(

    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,
)