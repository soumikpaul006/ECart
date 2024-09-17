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

//    private lateinit var binding:FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_payment, container, false)

        // Initialize the RadioGroup
        radioGroupPayment = view.findViewById(R.id.radioGroupPayment)

//        binding.btnNext.setOnClickListener {
//            // Navigate to the SummaryFragment
//
//        }

        // Set up any additional behavior if needed, like handling payment method selection
        radioGroupPayment.setOnCheckedChangeListener { group, checkedId ->
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

        return view
    }
}
