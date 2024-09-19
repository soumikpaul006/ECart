package com.example.loginregisterretrofit.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregisterretrofit.PreferenceHelper
import com.example.loginregisterretrofit.model.datalayer.AddUserRequest
import com.example.loginregisterretrofit.model.datalayer.AddUserResponse
import com.example.loginregisterretrofit.databinding.ActivityRegisterBinding
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import com.example.loginregisterretrofit.showMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val apiService: ApiService = ApiClient.retrofit.create(ApiService::class.java)
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        preferenceHelper = PreferenceHelper(this)


        binding.btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val fullname = binding.edtFullname.text.toString().trim()
        val mobileno = binding.edtMobileno.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()


        if (fullname.isEmpty() || mobileno.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showMessage("Error", "All fields are required.")
            return
        }


        val addUserRequest = AddUserRequest(fullname, mobileno, email, password)

        val call=apiService.registerUser(addUserRequest)

        call.enqueue(object : Callback<AddUserResponse> {
            override fun onResponse(call: Call<AddUserResponse>, response: Response<AddUserResponse>) {
                if (!response.isSuccessful) {
                    showMessage("Error", "Failed to register. Please try again.")
                    return
                }

                val result = response.body()
                if (result?.status == 0) {

                    preferenceHelper.saveRegisterUserSession(email, fullname,mobileno)
                    showMessage("Success", result.message)
                    navigateToUserPage()

                } else if (result?.status == 1) {
                    showMessage("Error", result.message)
                }
            }

            override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                showMessage("Error", "Network error: ${t.message}")
            }
        })
    }

    private fun navigateToUserPage() {
        val intent = Intent(this@RegisterActivity, UserPageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

