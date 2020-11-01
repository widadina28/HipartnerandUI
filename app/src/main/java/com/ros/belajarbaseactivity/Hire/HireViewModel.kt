package com.ros.belajarbaseactivity.Hire

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.Project.ProjectModel
import com.ros.belajarbaseactivity.Project.ProjectResponse
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HireViewModel : ViewModel() {
    val isHireLiveData = MutableLiveData<Boolean>()
    val isProjectSpinnerLiveData = MutableLiveData<List<ProjectModel>>()
    private var selectedProject = ""

    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil

    fun setSharedPreference(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun setHireService(service: AuthApiService) {
        this.service = service
    }

    fun callApi(Description: String, Price: String, Date: String) {
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)

        service?.postHire(
            selectedProject, "$idEngineer", Description,
            Price, "Pending", Date
        )
            ?.enqueue(object : Callback<HireResponse> {
                override fun onFailure(call: Call<HireResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<HireResponse>,
                    response: Response<HireResponse>
                ) {
                    isHireLiveData.value = true
                }
            })
    }

    fun spinnerProject(spinner: Spinner) {

        val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)
        service?.getProject(idComp)?.enqueue(object : Callback<ProjectResponse> {
            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ProjectResponse>,
                response: Response<ProjectResponse>
            ) {
                val listProject = response.body()?.data?.map {
                    ProjectModel(it.idProject.orEmpty(), it.projectName.orEmpty())
                } ?: listOf()
                isProjectSpinnerLiveData.value = listProject
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedProject = listProject[position].idProject.toString()
                    }
                }
            }

        })
    }


}