package com.ros.belajarbaseactivity.onboardvp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityViewpageronboardBinding
import com.ros.belajarbaseactivity.login.Login
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil

class viewpageronboard : BaseActivity() {
    private lateinit var binding: ActivityViewpageronboardBinding
    private lateinit var vpadapter : ViewPagerAdapter
//    lateinit var sharedpref: sharedprefutil

    override fun initBinding() {
        binding=DataBindingUtil.setContentView(this, R.layout.activity_viewpageronboard)
    }

    override fun onCreateActivity() {
        vpadapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewpager.adapter=vpadapter
//        sharedpref = sharedprefutil(applicationContext)
//        startActivity(Intent(this, Login::class.java))
//        finish()
    }

//    override fun onStart() {
//        super.onStart()
//        if (sharedpref.getBoolean(Constant.PREF_IS_LOGIN)) {
//            startActivity(Intent(this, Login::class.java))
//            finish()
//        }
//    }

    override fun initListener() {}
}