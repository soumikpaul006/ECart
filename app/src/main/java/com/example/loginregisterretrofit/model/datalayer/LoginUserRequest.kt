package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class LoginUserRequest(

    @SerializedName("email_id")
    val email_id: String,

    @SerializedName("password")
    val password: String
)