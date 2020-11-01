package com.ros.belajarbaseactivity.webview


import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityGithubWebVBinding


class GithubWebVActivity : BaseActivity() {
    private lateinit var binding: ActivityGithubWebVBinding

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github_web_v)
    }

    override fun onCreateActivity() {
        binding.webview.loadUrl("https://github.com/widadina28")
    }

    override fun initListener() {
    }
}