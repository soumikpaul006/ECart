import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregisterretrofit.databinding.ItemAddressBinding
import com.example.loginregisterretrofit.model.datalayer.Addresse


class AddressAdapter(
    private var addressList: List<Addresse>,
    private var selectedPosition: Int = -1,
    private val onAddressSelected: (Addresse) -> Unit
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    inner class AddressViewHolder(private val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Addresse, position: Int) {
            binding.tvTitle.text = address.title
            binding.tvAddress.text = address.address
            binding.radioButton.isChecked = selectedPosition == position

            binding.radioButton.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()

                // notify the fragment of the selected address
                onAddressSelected(address)
            }

            // Handle item click (in case user clicks outside the radio button but still wants to select the address)
            binding.root.setOnClickListener {
                binding.radioButton.performClick()
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

    fun updateAddressList(newAddressList: List<Addresse>) {
        addressList = newAddressList
        notifyDataSetChanged()
    }
}



