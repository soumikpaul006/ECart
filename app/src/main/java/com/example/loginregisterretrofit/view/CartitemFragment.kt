package com.example.loginregisterretrofit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginregisterretrofit.CartAdapter
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.databinding.FragmentCartitemBinding
import com.example.loginregisterretrofit.model.AppDatabase
import com.example.loginregisterretrofit.model.dao.ProductDao
import com.example.loginregisterretrofit.viewmodel.CartViewModel

class CartitemFragment : Fragment() {

    private lateinit var binding: FragmentCartitemBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: CartAdapter
    private lateinit var productDao: ProductDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartitemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]


        productDao = AppDatabase.getInstance(requireContext()).productDao()


        binding.recyclerViewCartItems.layoutManager = LinearLayoutManager(requireContext())

        // Observe cart items from ViewModel and update RecyclerView
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            adapter = CartAdapter(cartItems, productDao) { totalPrice ->
                updateTotalPrice(totalPrice)
            }
            binding.recyclerViewCartItems.adapter = adapter


            val initialTotalPrice = cartItems.sumOf { it.quantity * it.price.toDouble() }
            updateTotalPrice(initialTotalPrice)
        }


        binding.btnNext.setOnClickListener {
            (activity as CheckoutActivity).binding.viewPager.currentItem = 1
        }
    }

    private fun updateTotalPrice(totalPrice: Double) {
        binding.txtTotalPrice.text = "Total: $${String.format("%.2f", totalPrice)}"
    }
}
