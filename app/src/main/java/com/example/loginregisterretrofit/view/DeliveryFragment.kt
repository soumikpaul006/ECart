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
import com.example.loginregisterretrofit.model.datalayer.AddAddressResponse
import com.example.loginregisterretrofit.model.datalayer.Address
import com.example.loginregisterretrofit.model.datalayer.Addresse
import com.example.loginregisterretrofit.model.datalayer.DeliveryAddress
import com.example.loginregisterretrofit.model.datalayer.GetUserAddressResponse

import com.example.loginregisterretrofit.viewmodel.AddressViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DeliveryFragment : Fragment() {

    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var adapter: AddressAdapter
    private lateinit var addressViewModel: AddressViewModel
    private lateinit var preferenceHelper: PreferenceHelper
    private var selectedAddress: DeliveryAddress? = null

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

        // Initialize the adapter with the callback to capture the selected address
        adapter = AddressAdapter(emptyList(), onAddressSelected = { address ->
            selectedAddress = DeliveryAddress(title = address.title, address = address.address)
        })


        binding.recyclerViewAddresses.adapter = adapter
        binding.recyclerViewAddresses.layoutManager = LinearLayoutManager(requireContext())

        // Fetch addresses for the particular user and update the adapter
        addressViewModel.getAddressesForUser(userId).observe(viewLifecycleOwner) { addresses ->
            adapter.updateAddressList(addresses)
        }


        binding.btnAddAddress.setOnClickListener {
            showAddAddressDialog(userId)
        }


        binding.btnNext.setOnClickListener {
            if (selectedAddress != null) {
                // Pass the selected address to CheckoutActivity
                (activity as CheckoutActivity).selectedAddress = selectedAddress!!

                // Move to the PaymentFragment
                (activity as CheckoutActivity).binding.viewPager.currentItem = 2
            } else {
                Toast.makeText(requireContext(), "Please select an address", Toast.LENGTH_SHORT).show()
            }
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


//                    val call = addressViewModel.apiService.postAddress(newAddressRequest)
//
//                    call.enqueue(object : Callback<AddAddressResponse> {
//                        override fun onResponse(call: Call<AddAddressResponse>, response: Response<AddAddressResponse>) {
//                            if (response.isSuccessful && response.body()?.status == 0) {
//
//                                Toast.makeText(requireContext(), "Address added successfully", Toast.LENGTH_SHORT).show()
//
//                                val fetchAddressesCall = addressViewModel.apiService.getUserAddresses(userId)
//
//                                fetchAddressesCall.enqueue(object : Callback<GetUserAddressResponse> {
//                                    override fun onResponse(call: Call<GetUserAddressResponse>, response: Response<GetUserAddressResponse>) {
//                                        if (response.isSuccessful && response.body()?.status == 0) {
//
//                                            response.body()?.addresses?.let { addresses ->
//                                                adapter.updateAddressList(addresses)
//                                            }
//                                        } else {
//                                            Toast.makeText(requireContext(), "Failed to fetch updated addresses", Toast.LENGTH_SHORT).show()
//                                        }
//                                    }
//
//                                    override fun onFailure(call: Call<GetUserAddressResponse>, t: Throwable) {
//                                        Toast.makeText(requireContext(), "Error fetching addresses: ${t.message}", Toast.LENGTH_SHORT).show()
//                                    }
//                                })
//                            } else {
//
//                                Toast.makeText(requireContext(), response.body()?.message ?: "Failed to add address", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                        override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
//
//                            Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//                        }
//                    })


                    //making the api call postAdress to POST the address
                    addressViewModel.postAddress(newAddressRequest, onSuccess = {
                        Toast.makeText(requireContext(), "Address added successfully", Toast.LENGTH_SHORT).show()

                        // update the adapter by observing the LiveData
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





