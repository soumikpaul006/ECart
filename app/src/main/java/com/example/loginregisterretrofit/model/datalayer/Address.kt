package com.example.loginregisterretrofit.model.datalayer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table")
data class Address(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val addressLine: String,
    val city: String,
    val state: String,
    val zipCode: String
)
