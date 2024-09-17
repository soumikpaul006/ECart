package com.example.loginregisterretrofit

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class CheckoutpagerAdapter(

    private val fragments:List<Fragment>,
    fragmentManager: FragmentActivity

): FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int)=fragments[position]
}