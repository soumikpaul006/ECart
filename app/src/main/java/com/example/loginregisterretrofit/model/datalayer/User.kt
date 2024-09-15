package com.example.loginregisterretrofit.model.datalayer

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("user_id")
    val user_id: String,

    @SerializedName("full_name")
    val full_name: String,

    @SerializedName("mobile_no")
    val mobile_no: String,

    @SerializedName("email_id")
    val email_id: String,
)