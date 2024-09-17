package com.example.loginregisterretrofit.model.datalayer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "product")
data class Product(

    @PrimaryKey
    @SerializedName("product_id")
    val product_id: String,

    @SerializedName("product_name")
    @ColumnInfo(name = "product_name")val product_name: String,

    @SerializedName("description")
    @ColumnInfo(name = "description") val description: String,

    @SerializedName("category_id")
    @ColumnInfo(name = "category_id") val category_id: String,

    @SerializedName("category_name")
    @ColumnInfo(name = "category_name") val category_name: String,

    @SerializedName("sub_category_id")
    @ColumnInfo(name = "sub_category_id") val sub_category_id: String,

    @SerializedName("subcategory_name")
    @ColumnInfo(name = "subcategory_name") val subcategory_name: String,

    @SerializedName("price")
    @ColumnInfo(name = "price") val price: String,

    @SerializedName("average_rating")
    @ColumnInfo(name = "average_rating") val average_rating: String,

    @SerializedName("product_image_url")
    @ColumnInfo(name = "product_image_url")val product_image_url: String,


    var quantity: Int = 1
)