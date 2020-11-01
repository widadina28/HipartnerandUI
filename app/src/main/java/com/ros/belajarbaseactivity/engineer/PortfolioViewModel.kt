package com.ros.belajarbaseactivity.engineer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Response

class PortfolioViewModel : ViewModel() {

    val isResponsePortfolio = MutableLiveData<List<PortofolioModel>>()
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

        service?.getPortofolioByID(idEngineer)
            ?.enqueue(object : retrofit2.Callback<PortofolioResponse> {
                override fun onFailure(call: Call<PortofolioResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<PortofolioResponse>,
                    response: Response<PortofolioResponse>
                ) {
                    val list = response.body()?.data?.map {
                        PortofolioModel(
                            it.idPortofolio.orEmpty(), it.aplicationName.orEmpty(),
                            it.image.orEmpty(), it.linkRepo.orEmpty()
                        )
                    } ?: listOf()
                    isResponsePortfolio.value = list
                }
            })

    }
}