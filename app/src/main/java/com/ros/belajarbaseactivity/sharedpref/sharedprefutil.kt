package com.ros.belajarbaseactivity.sharedpref

import android.content.Context
import android.content.SharedPreferences

class sharedprefutil (contex: Context) {

    private val Prefs_Name ="shared_pref"
    private val sharedpref : SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedpref=contex.getSharedPreferences(Prefs_Name, Context.MODE_PRIVATE)
        editor = sharedpref.edit()
    }

    fun putString(key: String, value:String?) {
        editor.putString(key, value)
            .apply()
    }


    fun getString(key: String) : String? {
        return sharedpref.getString(key, null)
    }

    fun putBoolean(key: String, value:Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String) : Boolean {
        return sharedpref.getBoolean(key, false)
    }
    fun clear (){
        editor.clear()
            .apply()
    }
}