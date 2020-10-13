package com.ros.belajarbaseactivity.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.bottomnav.BottomNavigationActivity
import com.ros.belajarbaseactivity.databinding.ActivityHomeBinding
import com.ros.belajarbaseactivity.profilecompany.FormCompany
import com.ros.belajarbaseactivity.register.Register
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil

class Login : BaseActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var sharedpref: sharedprefutil
    private lateinit var viewModel: LoginViewModel

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
    }

    override fun onCreateActivity() {
        sharedpref = sharedprefutil(applicationContext)
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setLoginService(service)
        }

        subscribeLiveData()
    }

    override fun initListener() {
        binding.btnLogin.setOnClickListener {
            viewModel.callAuthApi(binding.etEmail.text.toString(), binding.etPw.text.toString())
        }
        binding.btnRegist.setOnClickListener {
            baseStartActivity<Register>(context = this@Login)
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedpref.getBoolean(Constant.PREF_IS_LOGIN)) {
            baseStartActivity<BottomNavigationActivity>(context = this)
            finish()
        }
    }

    private fun subscribeLiveData(){
        viewModel.isLoginLiveData.observe(this, Observer {
            Log.d("android1", "$it")
            if (it) {
            Toast.makeText(this@Login,"Success" , Toast.LENGTH_SHORT).show()
            viewModel.isRegisterLiveData.observe(this, Observer {
                if (it){
                    startActivity(Intent(this@Login, FormCompany::class.java))
                    finish()
                }
                else {
                    startActivity(Intent(this@Login, BottomNavigationActivity::class.java))
                    finish()
                }
            })
            }
            else {
            Toast.makeText(this@Login, "FAILED", Toast.LENGTH_SHORT).show()

            }
        })

    }
}