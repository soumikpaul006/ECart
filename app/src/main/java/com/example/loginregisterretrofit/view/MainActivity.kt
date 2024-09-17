package com.example.loginregisterretrofit.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregisterretrofit.PreferenceHelper
import com.example.loginregisterretrofit.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Shared Preferences Helper
        preferenceHelper = PreferenceHelper(this)

        // Check if the user is already logged in
        if (preferenceHelper.isLoggedIn()) {
            // If user is logged in, redirect to UserPageActivity
            navigateToUserPage()

        } else {
            // Handle the register button click
            binding.btnRegister.setOnClickListener {
                startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
            }

            // Handle the login text click
            binding.txtLogin.setOnClickListener {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }
        }
    }

    private fun navigateToUserPage() {
        val intent = Intent(this@MainActivity, UserPageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  //FLAG_ACTIVITY_NEW_TASK: Starts the activity in a new task. //FLAG_ACTIVITY_CLEAR_TASK: Clears all previous activities in the task, so the new activity becomes the only one in the stack.
        startActivity(intent)
        finish()  // Close the MainActivity so the user cannot go back to it
    }
}