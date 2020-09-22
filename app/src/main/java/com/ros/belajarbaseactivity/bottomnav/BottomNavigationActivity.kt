package com.ros.belajarbaseactivity.bottomnav

import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityBottomNavigationBinding

class BottomNavigationActivity : BaseActivity() {
    private lateinit var binding:ActivityBottomNavigationBinding

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this
        ,R.layout.activity_bottom_navigation)
    }

    override fun onCreateActivity() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun initListener() {}
}