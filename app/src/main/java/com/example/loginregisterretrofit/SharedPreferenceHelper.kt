package com.example.loginregisterretrofit

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_PHONE_NO="phone_no"
    }

    fun saveUserSession(userId: String, email: String, name: String) {
        with(sharedPreferences.edit()) {
            putString(KEY_USER_ID, userId)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_NAME, name)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun saveRegisterUserSession(email: String, name: String,phone: String) {
        with(sharedPreferences.edit()) {
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_NAME, name)
            putString(KEY_PHONE_NO, phone)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }


    fun getUserEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(KEY_USER_NAME, null)
    }

    fun getPhoneNo(): String? {
        return sharedPreferences.getString(KEY_PHONE_NO, null)
    }

    fun clearUserSession() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }

}