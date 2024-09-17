package com.example.loginregisterretrofit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginregisterretrofit.CartAdapter
import com.example.loginregisterretrofit.viewmodel.CartViewModel
import com.example.loginregisterretrofit.databinding.FragmentCartBinding
import com.example.loginregisterretrofit.model.AppDatabase
import com.example.loginregisterretrofit.model.dao.ProductDao


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: CartAdapter
    private lateinit var productDao: ProductDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        // Initialize productDao from the database
        productDao = AppDatabase.getInstance(requireContext()).productDao()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())

        // Observe cart items and update RecyclerView
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            adapter = CartAdapter(cartItems, productDao) { totalPrice ->
                updateTotalPrice(totalPrice)
            }
            binding.recyclerViewCart.adapter = adapter

            // Update total price when cart items are loaded
            val initialTotalPrice = cartItems.sumOf { it.quantity * it.price.toDouble() }
            updateTotalPrice(initialTotalPrice)
        }
    }

    // Function to update total price in the UI
    private fun updateTotalPrice(totalPrice: Double) {
        binding.txtTotalPrice.text = "Total Price: $${String.format("%.2f", totalPrice)}"
    }
}
