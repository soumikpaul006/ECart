import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregisterretrofit.databinding.ItemAddressBinding
import com.example.loginregisterretrofit.model.datalayer.Addresse


class AddressAdapter(
    private var addressList: List<Addresse>,
    private var selectedPosition: Int = -1
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    inner class AddressViewHolder(private val binding: ItemAddressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Addresse, position: Int) {

            binding.tvTitle.text = address.title
            binding.tvAddress.text = address.address
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

    fun updateAddressList(newAddressList: List<Addresse>) {
        addressList = newAddressList
        notifyDataSetChanged()
    }
}


