package com.example.loginregisterretrofit.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregisterretrofit.CartAdapter
import com.example.loginregisterretrofit.viewmodel.CartViewModel
import com.example.loginregisterretrofit.databinding.FragmentCartBinding
import com.example.loginregisterretrofit.model.AppDatabase
import com.example.loginregisterretrofit.model.dao.ProductDao
import com.example.loginregisterretrofit.model.datalayer.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]


        productDao = AppDatabase.getInstance(requireContext()).productDao()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())

        // Observe cart items and update RecyclerView
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            adapter = CartAdapter(cartItems, productDao) { totalPrice ->
                updateTotalPrice(totalPrice)
            }

            binding.recyclerViewCart.adapter = adapter

            // Set up ItemTouchHelper for swipe-to-delete functionality
            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    // Get the product to delete
                    val position = viewHolder.adapterPosition
                    val productToDelete = cartItems[position]

                    // Delete the product from the database and update UI
                    deleteProduct(productToDelete)
                    adapter.notifyItemRemoved(position)
                }
            }

            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(binding.recyclerViewCart)

            // Update total price when cart items are loaded
            val initialTotalPrice = cartItems.sumOf { it.quantity * it.price.toDouble() }
            updateTotalPrice(initialTotalPrice)
        }
    }

    // Update total price in the UI
    private fun updateTotalPrice(totalPrice: Double) {
        binding.txtTotalPrice.text = "Total Price: $${String.format("%.2f", totalPrice)}"
    }


    // Delete a product from Room and update the UI
    private fun deleteProduct(product: Product) {
        lifecycleScope.launch(Dispatchers.IO) {
            productDao.deleteProduct(product)
        }
    }
}
