package com.ros.belajarbaseactivity.bottomnav


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.engineer.EngineerModel
import com.ros.belajarbaseactivity.engineer.EngineerResponse
import com.ros.belajarbaseactivity.profilecompany.CompanyByIDResponse
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel : ViewModel() {
    val isResponseHome = MutableLiveData<List<EngineerModel>>()

    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil

    fun setSharedPreference(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun setHireService(service: AuthApiService) {
        this.service = service
    }

    fun callApi() {
        val idAcc = sharedpref.getString(Constant.PREF_ID_ACC)
        service?.getAllEngineer()?.enqueue(object : retrofit2.Callback<EngineerResponse> {
            override fun onFailure(call: Call<EngineerResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<EngineerResponse>,
                response: Response<EngineerResponse>
            ) {
                val list = response.body()?.data?.map {
                    EngineerModel(
                        it.idEngineer.orEmpty(),
                        it.nameEngineer.orEmpty(),
                        it.nameSkill.orEmpty(),
                        it.status.orEmpty(),
                        it.nameLoc.orEmpty(),
                        it.rate.orEmpty(),
                        it.image.orEmpty()
                    )
                } ?: listOf()
                isResponseHome.value = list

            }
        })
        service.getCompanyID(idAcc).enqueue(object : Callback<CompanyByIDResponse> {
            override fun onFailure(call: Call<CompanyByIDResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<CompanyByIDResponse>,
                response: Response<CompanyByIDResponse>
            ) {
                sharedpref.putString(Constant.PREF_ID_COMPANY, response.body()?.data?.idCompany)
            }
        })
    }
}