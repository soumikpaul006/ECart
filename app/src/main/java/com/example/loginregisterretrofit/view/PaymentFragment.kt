package com.example.loginregisterretrofit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {

    private lateinit var radioGroupPayment: RadioGroup

    private lateinit var binding:FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        binding.radioGroupPayment.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbCashOnDelivery -> {

                }
                R.id.rbInternetBanking -> {

                }
                R.id.rbDebitCreditCard -> {

                }
                R.id.rbPayPal -> {

                }
            }
        }

        binding.btnNext.setOnClickListener {
            (activity as CheckoutActivity).binding.viewPager.currentItem = 3
        }

        return binding.root
    }
}
