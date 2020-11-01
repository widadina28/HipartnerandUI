package com.ros.belajarbaseactivity.engineer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityLinkRepoBinding
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil

class LinkRepo : BaseActivity() {
    private lateinit var binding: ActivityLinkRepoBinding
    private lateinit var sharedpref: sharedprefutil

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_link_repo)
    }

    override fun onCreateActivity() {
        sharedpref = sharedprefutil(applicationContext)
        val link = sharedpref.getString(Constant.PREF_LINK_REPO)
        binding.webviewPortofolio.loadUrl("$link")
    }

    override fun initListener() {}

}