package com.ros.belajarbaseactivity.bottomnav

import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityBottomNavigationBinding
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil

class BottomNavigationActivity : BaseActivity() {
    private lateinit var binding:ActivityBottomNavigationBinding
    private lateinit var sharedpref : sharedprefutil

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this
        ,R.layout.activity_bottom_navigation)
    }

    override fun onCreateActivity() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)
        sharedpref = sharedprefutil(applicationContext)
        sharedpref.getString(Constant.PREF_ID_COMPANY)

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun initListener() {}
}