package com.example.loginregisterretrofit.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.loginregisterretrofit.model.datalayer.Address

@Dao
interface AddressDao {

    @Insert
    fun insert(address: Address)

    @Query("SELECT * FROM address_table WHERE userId = :userId")
    fun getAddressesForUser(userId: String): LiveData<List<Address>>

    @Delete
    fun delete(address: Address)
}