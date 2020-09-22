package com.ros.belajarbaseactivity

import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.databinding.ActivityMainBinding
import com.ros.belajarbaseactivity.onboardvp.viewpageronboard

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val timesplash : Long =4000

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun onCreateActivity() {
        Handler().postDelayed({
            baseStartActivity<viewpageronboard>(context = this)
            finish()
        }, timesplash)
    }
    override fun initListener() {}
}