package com.example.loginregisterretrofit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheckoutViewModel : ViewModel() {
    private val _selectedPaymentMethod = MutableLiveData<String>()
    val selectedPaymentMethod: LiveData<String> get() = _selectedPaymentMethod

    fun setSelectedPaymentMethod(method: String) {
        _selectedPaymentMethod.value = method
    }
}