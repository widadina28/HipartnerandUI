package com.ros.belajarbaseactivity

import android.content.Intent
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.bottomnav.BottomNavigationActivity
import com.ros.belajarbaseactivity.databinding.ActivityMainBinding
import com.ros.belajarbaseactivity.login.Login
import com.ros.belajarbaseactivity.onboardvp.ViewPagerAdapter
import com.ros.belajarbaseactivity.onboardvp.viewpageronboard
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val timesplash: Long = 4000
    lateinit var sharedpref: sharedprefutil

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreateActivity() {
        sharedpref = sharedprefutil(applicationContext)
        val token = sharedpref.getString(Constant.PREF_TOKEN)
        Handler().postDelayed({
            if (token != null) {
                startActivity(Intent(this, BottomNavigationActivity::class.java))
                finish()
            } else {
                baseStartActivity<viewpageronboard>(context = this)
            }
        }, timesplash)
    }

    override fun initListener() {}
}