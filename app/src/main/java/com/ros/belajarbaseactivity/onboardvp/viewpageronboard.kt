package com.ros.belajarbaseactivity.onboardvp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityViewpageronboardBinding

class viewpageronboard : BaseActivity() {
    private lateinit var binding: ActivityViewpageronboardBinding
    private lateinit var vpadapter : ViewPagerAdapter

    override fun initBinding() {
        binding=DataBindingUtil.setContentView(this, R.layout.activity_viewpageronboard)
    }

    override fun onCreateActivity() {
        vpadapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewpager.adapter=vpadapter
    }

    override fun initListener() {}
}