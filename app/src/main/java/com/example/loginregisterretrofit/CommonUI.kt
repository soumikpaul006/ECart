package com.example.loginregisterretrofit

import android.app.Activity
import android.app.AlertDialog

fun Activity.showMessage(title:String, msg:String)
{
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(msg)
        .create()
        .show()
}