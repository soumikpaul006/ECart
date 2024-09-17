package com.example.loginregisterretrofit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.loginregisterretrofit.model.AppDatabase
import com.example.loginregisterretrofit.model.Repository.AddressRepository
import com.example.loginregisterretrofit.model.datalayer.Address
import kotlinx.coroutines.launch

class AddressViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AddressRepository

    init {
        val addressDao = AppDatabase.getInstance(application).addressDao()
        repository = AddressRepository(addressDao)
    }

    fun getAddressesForUser(userId: String): LiveData<List<Address>> {
        return repository.getAddressesForUser(userId)
    }

    fun insert(address: Address) = viewModelScope.launch {
        repository.insert(address)
    }

    fun delete(address: Address) = viewModelScope.launch {
        repository.delete(address)
    }
}