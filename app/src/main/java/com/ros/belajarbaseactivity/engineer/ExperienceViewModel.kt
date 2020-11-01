package com.ros.belajarbaseactivity.engineer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Response

class ExperienceViewModel : ViewModel() {

    val isResponseExperience = MutableLiveData<List<ExperienceModel>>()
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

        service.getExperienceByID(idEngineer)
            .enqueue(object : retrofit2.Callback<ExperienceResponse> {
                override fun onFailure(call: Call<ExperienceResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<ExperienceResponse>,
                    response: Response<ExperienceResponse>
                ) {
                    val list = response.body()?.data?.map {
                        ExperienceModel(
                            it.idExperience.orEmpty(),
                            it.position.orEmpty(),
                            it.companyName.orEmpty(),
                            it.description.orEmpty(),
                            it.start.orEmpty(),
                            it.end.orEmpty()
                        )
                    } ?: listOf()
                    isResponseExperience.value = list
                }

            })
    }
}