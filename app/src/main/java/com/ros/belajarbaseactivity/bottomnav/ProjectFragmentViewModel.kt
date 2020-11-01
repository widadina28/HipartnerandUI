package com.ros.belajarbaseactivity.bottomnav

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.Project.ProjectAllModel
import com.ros.belajarbaseactivity.Project.ProjectResponse
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Response

class ProjectFragmentViewModel : ViewModel() {

    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil
    val isResponseProject = MutableLiveData<List<ProjectAllModel>>()
    val isDeleteProject = MutableLiveData<Void>()

    fun setSharedPreference(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun setHireService(service: AuthApiService) {
        this.service = service
    }

    fun callApi() {
        val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)

        service?.getProject(idComp)?.enqueue(object : retrofit2.Callback<ProjectResponse> {
            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ProjectResponse>,
                response: Response<ProjectResponse>
            ) {
                val list = response.body()?.data?.map {
                    ProjectAllModel(
                        it.idProject.orEmpty(), it.image.orEmpty(), it.projectName.orEmpty(),
                        it.deadline.orEmpty()
                    )
                } ?: listOf()
                isResponseProject.value = list
            }

        })
    }

    fun delete(id: String) {
        service?.deleteProject(id)?.enqueue(object : retrofit2.Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isDeleteProject.value = response.body()
            }

        })
    }

}