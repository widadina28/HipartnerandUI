package com.ros.belajarbaseactivity.login

import android.content.SharedPreferences
import android.text.BoringLayout
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.bottomnav.BottomNavigationActivity
import com.ros.belajarbaseactivity.profilecompany.FormCompany
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel : ViewModel(), CoroutineScope {
    val isLoginLiveData = MutableLiveData<Boolean>()
    val isRegisterLiveData = MutableLiveData<Boolean>()
    val isToastLoginLiveData = MutableLiveData<Boolean>()
    val isResponseLogin = MutableLiveData<Boolean>()

    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun setLoginService(service: AuthApiService) {
        this.service = service
    }

    fun callAuthApi(email: String, password: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.loginRequest(
                        email, password
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            Log.d("Login", "$response")
            if (response is LoginResponse) {
                isResponseLogin.value = true
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    isToastLoginLiveData.value = true
                    if (response.data?.role == "company") {
                        sharedpref.putString(Constant.PREF_TOKEN, response.data.token)
                        sharedpref.putBoolean(Constant.PREF_IS_LOGIN, true)
                        sharedpref.putString(Constant.PREF_ID_ACC, response.data.id)
                        sharedpref.putString(Constant.PREF_NAME, response.data.name)
                        val reg = sharedpref.getBoolean(Constant.PREF_REGISTER)
                        isLoginLiveData.value = true
                        isRegisterLiveData.value = reg
                    } else {
                        isLoginLiveData.value = false
                    }
                } else {
                    isToastLoginLiveData.value = false
                }

            } else {
                isResponseLogin.value = false
            }
        }
    }
}