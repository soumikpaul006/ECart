package com.example.loginregisterretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginregisterretrofit.databinding.ProductItemViewBinding
import com.example.loginregisterretrofit.model.datalayer.ProductXX

class SearchResultsAdapter(private val productList: List<ProductXX>) :
    RecyclerView.Adapter<SearchResultsAdapter.ProductViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size


    inner class ProductViewHolder(private val binding: ProductItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductXX) {

            binding.txtProductName.text = product.product_name

            binding.txtProductDescription.text = product.description

            Glide.with(binding.imgProduct.context)
                .load("https://example.com/images/${product.product_image_url}")
                .into(binding.imgProduct)
        }
    }
}
