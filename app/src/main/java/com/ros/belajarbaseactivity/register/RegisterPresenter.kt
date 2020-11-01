package com.ros.belajarbaseactivity.register

import android.util.Log
import android.widget.Toast
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.login.Login
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterPresenter(
    private val coroutineScope: CoroutineScope,
    private val service: AuthApiService?
) : RegisterContract.Presenter {

    private var view: RegisterContract.View? = null
    private lateinit var sharedpref: sharedprefutil

    override fun bindToView(view: RegisterContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }


    override fun callApi(name: String, email: String, password: String) {
        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.registerRequest(name, email, password, "company")
                } catch (e: Throwable) {
                }
            }

            if (response is RegisterResponse) {
                view?.registerSucces()
            } else {
                view?.registerfailed()
            }
        }
    }

    override fun setSharedPref(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

}