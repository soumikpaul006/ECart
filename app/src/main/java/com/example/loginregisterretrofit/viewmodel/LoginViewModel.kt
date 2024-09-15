package com.example.loginregisterretrofit.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginregisterretrofit.model.datalayer.LoginUserRequest
import com.example.loginregisterretrofit.model.datalayer.LoginUserResponse
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    private val apiService = ApiClient.retrofit.create(ApiService::class.java)

    // LiveData to observe login response
    private val _loginResponse = MutableLiveData<LoginUserResponse>()
    val loginResponse: LiveData<LoginUserResponse> = _loginResponse

    // LiveData for error response
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Please enter all fields"
            return
        }

        val loginUserRequest = LoginUserRequest(email_id = email, password = password)

        val call=apiService.loginUser(loginUserRequest)

        call.enqueue(object : Callback<LoginUserResponse> {
            override fun onResponse(
                call: Call<LoginUserResponse>,
                response: Response<LoginUserResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    _loginResponse.postValue(response.body())
                } else {
                    _errorMessage.postValue("Failed to login user")
                }
            }

            override fun onFailure(call: Call<LoginUserResponse>, t: Throwable) {
                _errorMessage.postValue("Network error: ${t.message}")
            }
        })
    }
}