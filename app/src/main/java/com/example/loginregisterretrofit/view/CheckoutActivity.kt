package com.example.loginregisterretrofit.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.loginregisterretrofit.CheckoutPageAdapter
import com.example.loginregisterretrofit.PreferenceHelper
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.databinding.ActivityCheckoutBinding
import com.example.loginregisterretrofit.model.datalayer.DeliveryAddress
import com.example.loginregisterretrofit.viewmodel.CheckoutViewModel
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutActivity : AppCompatActivity() {

    lateinit var binding: ActivityCheckoutBinding
    private lateinit var viewModel: CheckoutViewModel
    private lateinit var preferenceHelper: PreferenceHelper

    // These variables will hold the selected address, payment method, and user ID to be accessed in SummaryFragment
    var selectedAddress: DeliveryAddress = DeliveryAddress("", "")
    var selectedPaymentMethod: String = ""
    var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[CheckoutViewModel::class.java]

        preferenceHelper = PreferenceHelper(this)

        userId = preferenceHelper.getUserId() ?: ""

        if (userId.isNotEmpty()) {

            println("User ID: $userId")
        } else {

            println("User ID not found. Please log in.")
        }

        val fragments = listOf(
            CartitemFragment(),
            DeliveryFragment(),
            PaymentFragment(),
            SummaryFragment()
        )

        val pagerAdapter = CheckoutPageAdapter(fragments, this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Cart Items"
                1 -> tab.text = "Delivery"
                2 -> tab.text = "Payment"
                3 -> tab.text = "Summary"
            }
        }.attach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("selectedPaymentMethod", selectedPaymentMethod)  // Save payment method
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        selectedPaymentMethod = savedInstanceState.getString("selectedPaymentMethod", "") // Restore payment method
    }
}
