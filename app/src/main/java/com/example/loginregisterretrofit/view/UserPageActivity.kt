package com.example.loginregisterretrofit.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.loginregisterretrofit.CategoryAdapter
import com.example.loginregisterretrofit.OnCategoryClickListener
import com.example.loginregisterretrofit.PreferenceHelper
import com.example.loginregisterretrofit.R
import com.example.loginregisterretrofit.model.datalayer.Category
import com.example.loginregisterretrofit.model.datalayer.ProductCategoryResponse
import com.example.loginregisterretrofit.databinding.ActivityUserPageBinding
import com.example.loginregisterretrofit.model.networklayer.ApiClient
import com.example.loginregisterretrofit.model.networklayer.ApiService
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserPageActivity : AppCompatActivity(), OnCategoryClickListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var preferenceHelper: PreferenceHelper
    private lateinit var binding: ActivityUserPageBinding
//    private lateinit var adapter: CategoryAdapter
    private lateinit var drawerToggle: ActionBarDrawerToggle
    val apiService = ApiClient.retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar as the ActionBar
        setSupportActionBar(binding.toolbar)

        // Set up DrawerLayout and ActionBarDrawerToggle
        drawerToggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, R.string.drawer_open, R.string.drawer_close
        )

        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()


        binding.navView.setNavigationItemSelectedListener(this)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        fetchCategories()

        preferenceHelper = PreferenceHelper(this)
//        binding.btnLogout.setOnClickListener {
//            preferenceHelper.clearUserSession()
//            val intent = Intent(this, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            finish()
//        }
    }

    // Fetch categories
    fun fetchCategories() {
        val call: Call<ProductCategoryResponse> = apiService.getProductCategory()

        call.enqueue(object : Callback<ProductCategoryResponse> {
            override fun onResponse(call: Call<ProductCategoryResponse>, response: Response<ProductCategoryResponse>) {
                if (!response.isSuccessful) {
                    Toast.makeText(this@UserPageActivity, "Failed to get category", Toast.LENGTH_SHORT).show()
                    return
                }

                val searchResult: ProductCategoryResponse? = response.body()
                searchResult?.let {
                    binding.recyclerView.adapter = CategoryAdapter(it.categories, this@UserPageActivity)
                }
            }

            override fun onFailure(call: Call<ProductCategoryResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@UserPageActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, UserPageActivity::class.java))
            }
            R.id.cart -> {
                startActivity(Intent(this, CartActivity::class.java))
            }
            R.id.orders -> {
                Toast.makeText(this, "Orders clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.profile -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.logout -> {
                preferenceHelper.clearUserSession()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START) // Close the drawer after a selection
        return true
    }

    // Handle back press to close drawer if open
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCategoryClick(category: Category) {
        val intent = Intent(this, ViewPagerActivity::class.java)
        intent.putExtra("CATEGORY_ID", category.category_id)
        startActivity(intent)
    }
}



