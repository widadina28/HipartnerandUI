//package com.ros.belajarbaseactivity
//
//import androidx.databinding.DataBindingUtil
//import com.ros.belajarbaseactivity.bottomnav.BottomNavigationActivity
//import com.ros.belajarbaseactivity.databinding.ActivityRegistBinding
//
//class regist : BaseActivity() {
//    private lateinit var binding: ActivityRegistBinding
//
//    override fun initBinding() {
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_regist)
//    }
//
//    override fun onCreateActivity() {
//        supportActionBar?.hide()
//    }
//
//    override fun initListener() {
//
//        binding.btnCreate.setOnClickListener {
//            baseStartActivity<BottomNavigationActivity>(context = this)
//        }
//    }
//}