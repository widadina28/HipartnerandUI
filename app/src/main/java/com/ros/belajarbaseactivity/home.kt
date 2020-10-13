//package com.ros.belajarbaseactivity
//
//import android.widget.Toast
//import androidx.databinding.DataBindingUtil
//import com.ros.belajarbaseactivity.bottomnav.BottomNavigationActivity
//import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
//import com.ros.belajarbaseactivity.databinding.ActivityHomeBinding
//import com.ros.belajarbaseactivity.sharedpref.Constant
//
//class home : BaseActivity() {
//    lateinit var binding: ActivityHomeBinding
//    lateinit var sharedpref : sharedprefutil
//
//    override fun initBinding() {
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
//    }
//
//    override fun onCreateActivity() {
//        supportActionBar?.hide()
//        sharedpref = sharedprefutil(applicationContext)
//    }
//
//    override fun initListener() {
//        var e_mail = binding.etEmail
//        var pw = binding.etPw
//        binding.btnLogin.setOnClickListener {
//            if (e_mail.text.isNotEmpty() && pw.text.isNotEmpty()) {
//                sharedpref.putString(Constant.PREF_EMAIL, e_mail.text.toString())
//                sharedpref.putString(Constant.PREF_PASSWORD, pw.text.toString())
//                sharedpref.putBoolean(Constant.PREF_IS_LOGIN, true)
//                Toast.makeText(applicationContext, "LOG IN SUCCESS", Toast.LENGTH_SHORT).show()
//                baseStartActivity<BottomNavigationActivity>(context = this)
//                finish()
//            }
//            else {Toast.makeText(applicationContext,"PLEASE LOGIN", Toast.LENGTH_SHORT).show()}
//        }
//        binding.btnRegist.setOnClickListener {
//           baseStartActivity<regist>(context = this)
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (sharedpref.getBoolean(Constant.PREF_IS_LOGIN)){
//            baseStartActivity<BottomNavigationActivity>(context = this)
//            finish()
//        }
//    }
//}