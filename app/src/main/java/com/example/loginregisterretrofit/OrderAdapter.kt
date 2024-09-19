package com.example.loginregisterretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregisterretrofit.databinding.OrderItemViewBinding
import com.example.loginregisterretrofit.model.datalayer.Order

class OrderAdapter(private var orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    fun setOrders(orders: List<Order>) {
        this.orders = orders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class OrderViewHolder(private val binding: OrderItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.txtOrderId.text = "Order ID: ${order.order_id}"
            binding.txtOrderAddress.text = "Address: ${order.address}"
            binding.txtOrderAmount.text = "Bill Amount: $${order.bill_amount}"
            binding.txtOrderDate.text = "Order Date: ${order.order_date}"
            binding.txtOrderStatus.text = "Status: ${order.order_status}"
        }
    }
}
