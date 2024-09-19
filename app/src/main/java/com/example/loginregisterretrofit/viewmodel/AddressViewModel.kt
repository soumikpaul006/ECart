package com.example.loginregisterretrofit.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginregisterretrofit.model.AppDatabase
import com.example.loginregisterretrofit.model.Repository.AddressRepository
import com.example.loginregisterretrofit.model.datalayer.AddAddressRequest
import com.example.loginregisterretrofit.model.datalayer.AddAddressResponse
import com.example.loginregisterretrofit.model.datalayer.Address
import com.example.loginregisterretrofit.model.datalayer.Addresse
import com.example.loginregisterretrofit.model.datalayer.GetUserAddressResponse
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressViewModel(application: Application) : AndroidViewModel(application) {

    val apiService = ApiClient.retrofit.create(ApiService::class.java)

    // Function to fetch addresses for the user
    fun getAddressesForUser(userId: String): LiveData<List<Addresse>> {


        val liveData = MutableLiveData<List<Addresse>>()


        apiService.getUserAddresses(userId).enqueue(object : Callback<GetUserAddressResponse> {
            override fun onResponse(call: Call<GetUserAddressResponse>, response: Response<GetUserAddressResponse>) {
                if (response.isSuccessful && response.body()?.status == 0) {
                    liveData.postValue(response.body()?.addresses)
                } else {
                    liveData.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<GetUserAddressResponse>, t: Throwable) {
                liveData.postValue(emptyList())
            }
        })
        return liveData
    }


    fun postAddress(addressRequest: AddAddressRequest, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {


            val call = apiService.postAddress(addressRequest)


            call.enqueue(object : Callback<AddAddressResponse> {
                override fun onResponse(call: Call<AddAddressResponse>, response: Response<AddAddressResponse>) {
                    if (response.isSuccessful && response.body()?.status == 0) {
                        onSuccess()
                    } else {
                        onFailure(response.body()?.message ?: "Failed to add address")
                    }
                }

                override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                    onFailure(t.message ?: "Unknown error occurred")
                }
            })
        }
    }
}

