package com.ros.belajarbaseactivity.engineer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityDetailEngineerBinding
import com.ros.belajarbaseactivity.onboardvp.ViewPagerAdapter

class DetailEngineer : BaseActivity() {
    private lateinit var binding: ActivityDetailEngineerBinding
    private lateinit var vpadapter : VPDetailEngineerAdapter

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_engineer)
    }

    override fun onCreateActivity() {
    vpadapter = VPDetailEngineerAdapter(supportFragmentManager)
        binding.viewpagerDetailEngineer.adapter=vpadapter
        binding.tabLayout.setupWithViewPager(binding.viewpagerDetailEngineer)
    }

    override fun initListener() {
    }
}