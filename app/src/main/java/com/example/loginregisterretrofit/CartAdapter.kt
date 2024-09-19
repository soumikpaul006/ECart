package com.example.loginregisterretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregisterretrofit.databinding.CartItemViewBinding
import com.example.loginregisterretrofit.model.dao.ProductDao
import com.example.loginregisterretrofit.model.datalayer.Product



class CartAdapter(
    private var cartList: List<Product>,
    private val productDao: ProductDao,
    private val updateTotalPriceCallback: (Double) -> Unit // Callback to notify when the total price changes
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    // set a new cart list when clearing the cart
    fun setCartList(newCartList: List<Product>) {
        this.cartList = newCartList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])
        // update the total price every time we bind an item
        updateTotalPriceCallback(getTotalPrice())
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    // Calculate the total price of items in the cart
    private fun getTotalPrice(): Double {
        return cartList.sumOf { it.quantity * it.price.toDouble() }
    }

    // Provide access to the cart items
    fun getCartItems(): List<Product> {
        return cartList
    }

    inner class CartViewHolder(private val binding: CartItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.txtProductName.text = product.product_name
            binding.txtProductQuantity.text = "Quantity: ${product.quantity}"
            binding.txtProductPrice.text = "Price: $${String.format("%.2f", product.price.toDouble())}"

            binding.btnIncreaseQuantity.setOnClickListener {
                product.quantity += 1
                updateItem(product)
            }

            binding.btnDecreaseQuantity.setOnClickListener {
                if (product.quantity > 1) {
                    product.quantity -= 1
                    updateItem(product)
                }
            }
        }

        // Update item in the cart and notify changes to the total price
        private fun updateItem(product: Product) {
            binding.txtProductQuantity.text = "Quantity: ${product.quantity}"
            val totalPriceForProduct = product.quantity * product.price.toDouble()
            binding.txtProductPrice.text = "Price: $${String.format("%.2f", totalPriceForProduct)}"

            // Update the product in the database using a background thread
            Thread {
                productDao.updateProduct(product)
            }.start()

            // Update the total price for all products in the cart
            updateTotalPriceCallback(getTotalPrice())
        }
    }
}




