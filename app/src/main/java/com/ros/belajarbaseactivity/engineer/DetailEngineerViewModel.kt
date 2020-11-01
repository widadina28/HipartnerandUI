package com.ros.belajarbaseactivity.engineer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Response

class DetailEngineerViewModel : ViewModel() {
    val isResponseDetail = MutableLiveData<EngineerResponseID>()

    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil

    fun setSharedPreference(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun setHireService(service: AuthApiService) {
        this.service = service
    }

    fun callApi() {
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)


        service?.getEngineerByID(idEngineer)
            ?.enqueue(object : retrofit2.Callback<EngineerResponseID> {
                override fun onFailure(call: Call<EngineerResponseID>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<EngineerResponseID>,
                    response: Response<EngineerResponseID>
                ) {
                    isResponseDetail.value = response.body()
                }

            })
    }
}