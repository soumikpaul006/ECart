package com.example.loginregisterretrofit.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginregisterretrofit.CheckoutPageAdapter
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.databinding.ActivityCheckoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutActivity : AppCompatActivity() {

    lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
}
