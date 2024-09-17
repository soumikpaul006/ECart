package com.example.loginregisterretrofit.view

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
import com.example.loginregisterretrofit.AddressAdapter
import com.example.loginregisterretrofit.PreferenceHelper
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.databinding.FragmentDeliveryBinding
import com.example.loginregisterretrofit.model.datalayer.Address
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

        // Observe the addresses for the for the particular user and update the adapter
        addressViewModel.getAddressesForUser(userId).observe(viewLifecycleOwner) { addresses ->
            adapter.updateAddressList(addresses)
        }

        binding.btnAddAddress.setOnClickListener {
            showAddAddressDialog(userId)
        }


        binding.btnNext.setOnClickListener{
            //GO TO PAYMENT FRAGMENT HAVING THE INFO OF THE ADDRESS

        }

    }

    private fun showAddAddressDialog(userId: String) {

        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_address, null)


        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add New Address")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->


                val addressLine = dialogView.findViewById<EditText>(R.id.etAddressLine).text.toString()
                val city = dialogView.findViewById<EditText>(R.id.etCity).text.toString()
                val state = dialogView.findViewById<EditText>(R.id.etState).text.toString()
                val zipCode = dialogView.findViewById<EditText>(R.id.etZipCode).text.toString()


                if (addressLine.isNotEmpty() && city.isNotEmpty() && state.isNotEmpty() && zipCode.isNotEmpty()) {

                    val newAddress = Address(0, userId, addressLine, city, state, zipCode)

                    //Inserting address in the room
                    addressViewModel.insert(newAddress)

                    Toast.makeText(requireContext(), "Address Added", Toast.LENGTH_SHORT).show()
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


