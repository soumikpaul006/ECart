package com.example.loginregisterretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregisterretrofit.databinding.CartItemViewBinding
import com.example.loginregisterretrofit.model.dao.ProductDao
import com.example.loginregisterretrofit.model.datalayer.Product

class CartAdapter(
    private val cartList: List<Product>,
    private val productDao: ProductDao,
    private val updateTotalPriceCallback: (Double) -> Unit // Callback to notify when total price changes
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    inner class CartViewHolder(private val binding: CartItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            // Set initial values for the product in the UI
            binding.txtProductName.text = product.product_name
            binding.txtProductQuantity.text = "Quantity: ${product.quantity}"
            binding.txtProductPrice.text = "Price: $${String.format("%.2f", product.price.toDouble())}"

            // Setup listener to increase quantity
            binding.btnIncreaseQuantity.setOnClickListener {
                product.quantity += 1
                updateItem(product)
            }

            // Setup listener to decrease quantity
            binding.btnDecreaseQuantity.setOnClickListener {
                if (product.quantity > 1) {
                    product.quantity -= 1
                    updateItem(product)
                }
            }
        }

        // Function to update item views and update quantity in the database
        private fun updateItem(product: Product) {
            // Update the quantity TextView
            binding.txtProductQuantity.text = "Quantity: ${product.quantity}"

            // Calculate and update the price for the individual product
            val totalPriceForProduct = product.quantity * product.price.toDouble()
            binding.txtProductPrice.text = "Price: $${String.format("%.2f", totalPriceForProduct)}"

            // Update the product in the Room database
            Thread { productDao.updateProduct(product) }.start()

            // Notify the fragment or activity to update the total cart price
            updateTotalPriceCallback(getTotalPrice())
        }
    }

    // Function to calculate the total price of all items in the cart
    private fun getTotalPrice(): Double {
        return cartList.sumOf { it.quantity * it.price.toDouble() }
    }
}


