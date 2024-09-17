package com.example.loginregisterretrofit.view

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {


    private lateinit var binding:ActivityCartBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheckout.setOnClickListener{
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, CartFragment())
                .commit()
        }
    }
}