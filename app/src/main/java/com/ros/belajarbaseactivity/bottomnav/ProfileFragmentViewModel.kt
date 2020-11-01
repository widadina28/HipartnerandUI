package com.ros.belajarbaseactivity.bottomnav

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.profilecompany.CompanyByIDResponse
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragmentViewModel : ViewModel() {
    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil
    val isResponseProfile = MutableLiveData<CompanyByIDResponse>()

    fun setSharedPreference(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun setHireService(service: AuthApiService) {
        this.service = service
    }

    fun callApi() {

        val idAcc = sharedpref.getString(Constant.PREF_ID_ACC)
        service?.getCompanyID(idAcc)?.enqueue(object : Callback<CompanyByIDResponse> {
            override fun onFailure(call: Call<CompanyByIDResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<CompanyByIDResponse>,
                response: Response<CompanyByIDResponse>
            ) {
                isResponseProfile.value = response.body()
                sharedpref.putString(Constant.PREF_ID_COMPANY, response.body()?.data?.idCompany)
            }

        })
    }

}