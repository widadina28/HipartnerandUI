package com.ros.belajarbaseactivity.register

import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityRegistBinding
import com.ros.belajarbaseactivity.login.Login
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import kotlinx.coroutines.*

class Register : BaseActivity(), RegisterContract.View {
    lateinit var binding: ActivityRegistBinding
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var presenter: RegisterContract.Presenter
    lateinit var sharedpref: sharedprefutil

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_regist)
    }

    override fun onCreateActivity() {
        sharedpref = sharedprefutil(applicationContext)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        presenter = RegisterPresenter(coroutineScope, service)
        presenter!!.setSharedPref(sharedpref)

    }

    override fun initListener() {
        binding.btnCreate.setOnClickListener {
            presenter?.callApi(
                binding.etName.text.toString(), binding.etEmailregist.text.toString(),
                binding.etPwregist.text.toString()
            )
        }
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)
    }

    override fun onStop() {
        presenter?.unbind()
        super.onStop()
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    override fun registerSucces() {
        val a = sharedpref.putBoolean(Constant.PREF_REGISTER, true)
        Toast.makeText(this@Register, "Success!", Toast.LENGTH_SHORT).show()
        baseStartActivity<Login>(context = this@Register)
        finish()
    }

    override fun registerfailed() {
        Toast.makeText(this@Register, "FAILED", Toast.LENGTH_SHORT).show()
    }
}