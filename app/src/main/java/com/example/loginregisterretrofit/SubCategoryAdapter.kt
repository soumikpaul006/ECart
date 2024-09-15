package com.example.loginregisterretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginregisterretrofit.model.datalayer.Product
import com.example.loginregisterretrofit.databinding.SubcategoryViewBinding

class SubCategoryAdapter(
    private val subcatList:List<Product>,
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

    inner class SubCategoryViewHolder(private val itemView:SubcategoryViewBinding):RecyclerView.ViewHolder(itemView.root)
    {
        fun setData(products: Product)
        {
            with(binding)
            {
                txtSubCategoryProductName.text=products.product_name
                txtSubCategoryProductDescription.text=products.description

                val imageUrl = "https://apolisrises.co.in/myshop/images/" + products.product_image_url
                Glide.with(imgSubCategory.context)
                    .load(imageUrl)
                    .into(imgSubCategory)

                val rating = products.average_rating.toFloatOrNull()

                if (rating != null) {
                    txtSubCategoryProductRating.rating = rating
                }

            }

        }
    }
}