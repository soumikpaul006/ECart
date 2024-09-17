package com.example.loginregisterretrofit.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.loginregisterretrofit.PreferenceHelper
import com.example.loginregisterretrofit.model.datalayer.LoginUserRequest
import com.example.loginregisterretrofit.model.datalayer.LoginUserResponse
import com.example.loginregisterretrofit.databinding.ActivityLoginBinding
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import com.example.loginregisterretrofit.showMessage
import com.example.loginregisterretrofit.viewmodel.LoginViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        preferenceHelper = PreferenceHelper(this)


        checkPreferenceExist()

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            viewModel.login(email, password)
        }


        observeViewModel()
    }


    // Check if the user is already logged in using shared preferences
    private fun checkPreferenceExist() {
        if (preferenceHelper.isLoggedIn()) {
            navigateToUserPage()
        }
    }

    private fun observeViewModel() {


        // Observe login response
        viewModel.loginResponse.observe(this) { response ->
            response?.let {
                if (it.status == 0) {
                    it.user?.let { user ->
                        preferenceHelper.saveUserSession(user.user_id, user.email_id, user.full_name)
                        showMessage("Success", "User logged in successfully")
                        navigateToUserPage()
                    }
                } else {
                    showMessage("Failed", it.message)
                }
            }
        }

        // Observe error messages
        viewModel.errorMessage.observe(this) { message ->
            showMessage("Error", message)
        }
    }

    private fun navigateToUserPage() {
        val intent = Intent(this@LoginActivity, UserPageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}