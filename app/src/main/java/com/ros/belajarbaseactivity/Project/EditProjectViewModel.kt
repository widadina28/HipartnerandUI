package com.ros.belajarbaseactivity.Project

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.Hire.HireProjectResponse
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProjectViewModel : ViewModel() {
    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil

    val responseGetProjectLiveData = MutableLiveData<ProjectByIDResponse>()
    val responsePostProjectLiveData = MutableLiveData<PostProjectResponse>()
    val responsePutProjectLiveData = MutableLiveData<Boolean>()
    val responseGetHireProject = MutableLiveData<HireProjectResponse>()

    fun setSharedPref(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun setEditProjectService(service: AuthApiService) {
        this.service = service
    }

    fun getProject() {
        val idProject = sharedpref.getString(Constant.PREF_ID_PROJECT)?.toInt()

        service?.getProjectByID(idProject)?.enqueue(object : Callback<ProjectByIDResponse> {
            override fun onFailure(call: Call<ProjectByIDResponse>, t: Throwable) {
                Log.d("getProject", "eror = $t")
            }

            override fun onResponse(
                call: Call<ProjectByIDResponse>,
                response: Response<ProjectByIDResponse>
            ) {
                responseGetProjectLiveData.value = response.body()
            }
        })
    }

    fun postProject(
        Name: RequestBody,
        Description: RequestBody,
        Deadline: RequestBody,
        idComp: RequestBody,
        Price: RequestBody,
        img: MultipartBody.Part?
    ) {
        service?.postProject(Name, Description, Deadline, idComp, Price, img)
            ?.enqueue(object : Callback<PostProjectResponse> {
                override fun onFailure(call: Call<PostProjectResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<PostProjectResponse>,
                    response: Response<PostProjectResponse>
                ) {
                    responsePostProjectLiveData.value = response.body()
                }
            })

    }

    fun putProject(
        Name: RequestBody,
        Description: RequestBody,
        Deadline: RequestBody,
        img: MultipartBody.Part?,
        idComp: RequestBody,
        Price: RequestBody
    ) {
        val idProject = sharedpref.getString(Constant.PREF_ID_PROJECT)

        service?.putProject(idProject, Name, Description, Deadline, img, idComp, Price)
            ?.enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    responsePutProjectLiveData.value = true
                }
            })
    }

    fun getHireProject() {
        val idProject = sharedpref.getString(Constant.PREF_ID_PROJECT)
        service.getHireProject(idProject).enqueue(object : Callback<HireProjectResponse> {
            override fun onFailure(call: Call<HireProjectResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<HireProjectResponse>,
                response: Response<HireProjectResponse>
            ) {
                responseGetHireProject.value = response.body()
            }

        })
    }


}
