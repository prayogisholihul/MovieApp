package com.example.MovieApp

import android.content.Context
import android.content.SharedPreferences

class sharedPref(val context: Context) {
    companion object {
        const val User_pref = "SharedPref"
    }

    var pref: SharedPreferences = context.getSharedPreferences(User_pref, 0)
    fun setValue(key: String, value: String) {
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putString(key, value)
        editor.apply()

    }

    fun getValue(key: String): String? {
        return pref.getString(key, "")
    }

    fun logout(){
        val editor:SharedPreferences.Editor=pref.edit()
        editor.clear()
        editor.apply()
    }
}