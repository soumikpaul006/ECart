package com.example.loginregisterretrofit.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginregisterretrofit.model.AppDatabase
import com.example.loginregisterretrofit.model.datalayer.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {


    private val productDao = AppDatabase.getInstance(application).productDao()
    val cartItems: LiveData<List<Product>> = productDao.getAllCartItems()

    fun addToCart(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.insertProduct(product)
        }
    }
}