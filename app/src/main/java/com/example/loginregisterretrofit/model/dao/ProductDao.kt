package com.example.loginregisterretrofit.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.loginregisterretrofit.model.datalayer.Product


@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product)

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

    @Query("DELETE FROM product")
    fun clearCart()

    @Query("SELECT * FROM product")
    fun getAllCartItems(): LiveData<List<Product>>
}