package com.example.loginregisterretrofit.view

import AddressAdapter
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.loginregisterretrofit.PreferenceHelper
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.databinding.FragmentDeliveryBinding
import com.example.loginregisterretrofit.model.datalayer.AddAddressRequest

import com.example.loginregisterretrofit.viewmodel.AddressViewModel


class DeliveryFragment : Fragment() {

    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var adapter: AddressAdapter
    private lateinit var addressViewModel: AddressViewModel
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addressViewModel = ViewModelProvider(this)[AddressViewModel::class.java]
        preferenceHelper = PreferenceHelper(requireContext())


        val userId = preferenceHelper.getUserId() ?: ""


        adapter = AddressAdapter(emptyList())
        binding.recyclerViewAddresses.adapter = adapter
        binding.recyclerViewAddresses.layoutManager = LinearLayoutManager(requireContext())

        // fetch addresses for the particular user and update the adapter
        addressViewModel.getAddressesForUser(userId).observe(viewLifecycleOwner) { addresses ->
            adapter.updateAddressList(addresses)
        }

        binding.btnAddAddress.setOnClickListener {
            showAddAddressDialog(userId)
        }

        // proceed to the next step (payment) with selected address
        binding.btnNext.setOnClickListener {

        }
    }

    private fun showAddAddressDialog(userId: String) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_address, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add New Address")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->



                val title = dialogView.findViewById<EditText>(R.id.etTitle).text.toString()
                val addressLine = dialogView.findViewById<EditText>(R.id.etAddress).text.toString()


                if (title.isNotEmpty() && addressLine.isNotEmpty()) {



                    val newAddressRequest = AddAddressRequest(user_id = userId.toInt(), title = title, address = addressLine)



                    // Make API call to submit the new address
                    addressViewModel.postAddress(newAddressRequest, onSuccess = {
                        Toast.makeText(requireContext(), "Address added successfully", Toast.LENGTH_SHORT).show()

                        // Directly update the adapter by observing the LiveData
                        addressViewModel.getAddressesForUser(userId).observe(viewLifecycleOwner) { addresses ->
                            adapter.updateAddressList(addresses)
                        }



                    }, onFailure = {
                        Toast.makeText(requireContext(), "Failed to add address", Toast.LENGTH_SHORT).show()
                    })



                } else {
                    Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }



                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }
}





