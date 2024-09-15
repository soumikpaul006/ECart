package com.example.loginregisterretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginregisterretrofit.model.datalayer.Category
import com.example.loginregisterretrofit.databinding.ProductViewBinding

class CategoryAdapter(
    private val catList:List<Category>,
    private val listener: OnCategoryClickListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>()
{

    private lateinit var binding:ProductViewBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        binding=ProductViewBinding.inflate(layoutInflater,parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.setData(catList[position])
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    inner class CategoryViewHolder(private val itemView:ProductViewBinding): RecyclerView.ViewHolder(itemView.root) {

        fun setData(category: Category)
        {
            with(binding)
            {
                txtCategoryName.text=category.category_name

                val imageUrl = "https://apolisrises.co.in/myshop/images/" + category.category_image_url

                Glide.with(imgCategory.context)
                    .load(imageUrl)
                    .into(imgCategory)

                root.setOnClickListener{
                    listener.onCategoryClick(category)
                }
            }
        }
    }
}