package com.example.loginregisterretrofit.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.databinding.FragmentPaymentBinding
import com.example.loginregisterretrofit.viewmodel.CheckoutViewModel


class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private var selectedPaymentMethod: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        binding.radioGroupPayment.setOnCheckedChangeListener { _, checkedId ->
            selectedPaymentMethod = when (checkedId) {
                R.id.rbCashOnDelivery -> "COD"
                R.id.rbInternetBanking -> "Internet Banking"
                R.id.rbDebitCreditCard -> "Debit/Credit Card"
                R.id.rbPayPal -> "PayPal"
                else -> ""
            }
        }

        binding.btnNext.setOnClickListener {
            if (selectedPaymentMethod.isNotEmpty()) {


//                Log.d("PaymentFragment", "Selected Payment Method: $selectedPaymentMethod")
//                (activity as CheckoutActivity).selectedPaymentMethod = selectedPaymentMethod


                val viewModel = ViewModelProvider(requireActivity())[CheckoutViewModel::class.java]
                viewModel.setSelectedPaymentMethod(selectedPaymentMethod)


                (activity as CheckoutActivity).binding.viewPager.currentItem = 3
            } else {
                Toast.makeText(requireContext(), "Please select a payment method", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}


