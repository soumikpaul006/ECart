package com.example.loginregisterretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginregisterretrofit.model.datalayer.Product
import com.example.loginregisterretrofit.databinding.SubcategoryViewBinding

class SubCategoryAdapter(
    private val subcatList:List<Product>,
    private val addToCartCallback: (Product) -> Unit
):RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder>() {

    private lateinit var binding:SubcategoryViewBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCategoryAdapter.SubCategoryViewHolder {

        val layoutInflater= LayoutInflater.from(parent.context)
        binding= SubcategoryViewBinding.inflate(layoutInflater,parent,false)
        return SubCategoryViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SubCategoryAdapter.SubCategoryViewHolder, position: Int) {
            holder.setData(subcatList[position])
    }

    override fun getItemCount(): Int {
        return subcatList.size
    }

    inner class SubCategoryViewHolder(private val binding: SubcategoryViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(product: Product) {
            with(binding) {
                txtSubCategoryProductName.text = product.product_name
                txtSubCategoryProductDescription.text = product.description

                // Load image using Glide or any image loader
                Glide.with(imgSubCategory.context)
                    .load("https://apolisrises.co.in/myshop/images/${product.product_image_url}")
                    .into(imgSubCategory)

                btnAddToCart.setOnClickListener {
                    addToCartCallback(product) // Pass the selected product to the callback
                }
            }
        }
    }
}
