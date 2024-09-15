package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class LoginUserResponse(

    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("user")
    val user: User
)