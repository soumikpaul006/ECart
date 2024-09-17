package com.example.loginregisterretrofit.model.networklayer

import com.example.loginregisterretrofit.model.datalayer.AddAddressRequest
import com.example.loginregisterretrofit.model.datalayer.AddAddressResponse
import com.example.loginregisterretrofit.model.datalayer.AddUserRequest
import com.example.loginregisterretrofit.model.datalayer.AddUserResponse
import com.example.loginregisterretrofit.model.datalayer.GetUserAddressResponse
import com.example.loginregisterretrofit.model.datalayer.LoginUserRequest
import com.example.loginregisterretrofit.model.datalayer.LoginUserResponse
import com.example.loginregisterretrofit.model.datalayer.ProductCategoryResponse
import com.example.loginregisterretrofit.model.datalayer.SubCategoryProductResponse
import com.example.loginregisterretrofit.model.datalayer.SubCategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Content-type: application/json")
    @POST("User/register")
    fun registerUser(
        @Body apr: AddUserRequest
    ): Call<AddUserResponse>


    @Headers("Content-type: application/json")
    @POST("User/auth")
    fun loginUser(
        @Body apr: LoginUserRequest
    ): Call<LoginUserResponse>


    @GET("Category")
    fun getProductCategory(

    ):Call<ProductCategoryResponse>



    @GET("SubCategory")
    fun getSubCategory(
        @Query("category_id") scid: String
    ): Call<SubCategoryResponse>



    @GET("SubCategory/products/{sub_category_id}")
    fun getSubCategoryProduct(
        @Path("sub_category_id") scid:Int
    ):Call<SubCategoryProductResponse>


    //SEARCH PRODUCT




    //PRODUCT DETAILS




    //ADD ADDRESS
    @POST("User/address")
    @Headers("Content-type: application/json")
    fun postAddress(
        @Body addressRequest: AddAddressRequest
    ): Call<AddAddressResponse>


    //GET LIST OF ADDRESS
    @GET("User/addresses/{user_id}")
    fun getUserAddresses(
        @Path("user_id") userId: String
    ): Call<GetUserAddressResponse>

    //PLACE ORDER




    //LOGOUT




}