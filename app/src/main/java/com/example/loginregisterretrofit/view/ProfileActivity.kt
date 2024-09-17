package com.example.loginregisterretrofit.view


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregisterretrofit.PreferenceHelper
import com.example.loginregisterretrofit.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding:ActivityProfileBinding
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceHelper = PreferenceHelper(this)

        val fullName = preferenceHelper.getUserName()
        val email = preferenceHelper.getUserEmail()
        val mobileNumber = preferenceHelper.getPhoneNo()

        binding.tvFullName.text = "Full Name: $fullName"
        binding.tvEmail.text = "Email: $email"
        binding.tvMobileNumber.text = "Mobile Number: $mobileNumber"

    }
}