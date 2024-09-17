package com.example.loginregisterretrofit.model.Repository

import androidx.lifecycle.LiveData
import com.example.loginregisterretrofit.model.dao.AddressDao
import com.example.loginregisterretrofit.model.datalayer.Address

class AddressRepository(private val addressDao: AddressDao) {

    fun getAddressesForUser(userId: String): LiveData<List<Address>> {
        return addressDao.getAddressesForUser(userId)
    }

    fun insert(address: Address) {
        addressDao.insert(address)
    }

    fun delete(address: Address) {
        addressDao.delete(address)
    }
}