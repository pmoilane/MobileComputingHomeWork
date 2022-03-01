package com.example.mobilecomputinghw.data.entity

import android.content.Context

class SPreference(context: Context) {

    val sharedPreference = context.getSharedPreferences("LoginData", Context.MODE_PRIVATE)
    var editor = sharedPreference.edit()

}