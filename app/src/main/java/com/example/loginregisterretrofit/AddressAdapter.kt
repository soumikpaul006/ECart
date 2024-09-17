package com.example.loginregisterretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregisterretrofit.databinding.ItemAddressBinding
import com.example.loginregisterretrofit.model.datalayer.Address

class AddressAdapter(
    private var addressList: List<Address>,
    private var selectedPosition: Int = -1
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    inner class AddressViewHolder(private val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address, position: Int) {

            binding.tvAddress.text = "${address.addressLine}, ${address.city}, ${address.state}, ${address.zipCode}"
            binding.radioButton.isChecked = selectedPosition == position

            binding.radioButton.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(addressList[position], position)
    }

    override fun getItemCount(): Int = addressList.size

    // to get the currently selected address
    fun getSelectedAddress(): Address? {
        return if (selectedPosition != -1) {
            addressList[selectedPosition]
        } else {
            null
        }
    }

    // Update the address list in case of data change
    fun updateAddressList(newAddressList: List<Address>) {
        addressList = newAddressList
        notifyDataSetChanged()
    }
}
