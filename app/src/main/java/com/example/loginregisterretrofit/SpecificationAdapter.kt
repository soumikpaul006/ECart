package com.example.loginregisterretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginregisterretrofit.databinding.ItemSpecificationBinding
import com.example.loginregisterretrofit.model.datalayer.Specification

class SpecificationAdapter(
    private val specificationList: List<Specification>
) : RecyclerView.Adapter<SpecificationAdapter.SpecificationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecificationViewHolder {
        val binding = ItemSpecificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecificationViewHolder, position: Int) {
        holder.bind(specificationList[position])
    }

    override fun getItemCount(): Int = specificationList.size


    inner class SpecificationViewHolder(private val binding: ItemSpecificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(specification: Specification) {
            binding.txtSpecificationTitle.text = specification.title
            binding.txtSpecificationValue.text = specification.specification
        }
    }
}