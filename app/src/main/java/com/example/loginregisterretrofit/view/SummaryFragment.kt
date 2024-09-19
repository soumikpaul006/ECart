package com.example.loginregisterretrofit.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginregisterretrofit.CartAdapter
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.databinding.FragmentSummaryBinding
import com.example.loginregisterretrofit.model.AppDatabase
import com.example.loginregisterretrofit.model.dao.ProductDao
import com.example.loginregisterretrofit.model.datalayer.Address
import com.example.loginregisterretrofit.model.datalayer.DeliveryAddress
import com.example.loginregisterretrofit.model.datalayer.Item
import com.example.loginregisterretrofit.model.datalayer.OrderRequest
import com.example.loginregisterretrofit.model.datalayer.OrderResponse
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import com.example.loginregisterretrofit.viewmodel.CartViewModel
import com.example.loginregisterretrofit.viewmodel.CheckoutViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var adapter: CartAdapter
    private lateinit var selectedAddress: DeliveryAddress
    private var totalPrice: Double = 0.0
    private lateinit var productDao: ProductDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(requireActivity())[CheckoutViewModel::class.java]

        // Retrieve the selected address from the activity
        val checkoutActivity = activity as CheckoutActivity
        selectedAddress = checkoutActivity.selectedAddress

        // Observe the selected payment method from the ViewModel
        viewModel.selectedPaymentMethod.observe(viewLifecycleOwner) { method ->
            binding.txtSelectedPaymentMethod.text = if (method != null) {
                "Payment Method: $method"
            } else {
                "No Payment Method Selected"
            }
        }

        productDao = AppDatabase.getInstance(requireContext()).productDao()

        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.txtSelectedAddress.text = "Delivery Address: ${selectedAddress.title}, ${selectedAddress.address}"

        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            adapter = CartAdapter(cartItems, productDao) { total ->
                totalPrice = total
                binding.txtTotalPrice.text = "Total Price: $${String.format("%.2f", totalPrice)}"
            }
            binding.recyclerViewCartItems.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerViewCartItems.adapter = adapter
        }

        binding.btnPlaceOrder.setOnClickListener {
            placeOrder()
        }
    }

    private fun placeOrder() {
        val userId = (activity as CheckoutActivity).userId
        val orderItems = adapter.getCartItems().map {
            Item(
                product_id = it.product_id.toInt(),
                quantity = it.quantity,
                unit_price = it.price.toInt()
            )
        }

        val orderRequest = OrderRequest(
            user_id = userId.toInt(),
            delivery_address = selectedAddress,
            items = orderItems,
            bill_amount = totalPrice.toInt(),
            payment_method = binding.txtSelectedPaymentMethod.text.toString().removePrefix("Payment Method: ")
        )

        val apiService = ApiClient.retrofit.create(ApiService::class.java)

        val call = apiService.placeOrder(orderRequest)

        call.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {

                if (response.isSuccessful && response.body()?.status == 0) {

                    val orderId = response.body()?.order_id

                    Toast.makeText(requireContext(), "Order placed successfully! Order ID: $orderId", Toast.LENGTH_SHORT).show()


                    clearCart()


                    hideSummaryUI()


                    val intent = Intent(requireContext(), OrderActivity::class.java)
                    intent.putExtra("order_id", orderId)
                    startActivity(intent)

                } else {
                    Toast.makeText(requireContext(), "Failed to place order: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun clearCart() {
        Thread {
            productDao.clearCart()
        }.start()

        adapter.setCartList(emptyList())
        binding.txtTotalPrice.text = "Total Price: $0.00"
    }

    private fun hideSummaryUI() {
        binding.txtSelectedAddress.visibility = View.GONE
        binding.txtSelectedPaymentMethod.visibility = View.GONE
        binding.recyclerViewCartItems.visibility = View.GONE
        binding.txtTotalPrice.visibility = View.GONE
        binding.btnPlaceOrder.visibility = View.GONE
    }
}






