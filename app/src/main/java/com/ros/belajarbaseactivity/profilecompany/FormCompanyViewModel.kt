package com.ros.belajarbaseactivity.profilecompany

import android.content.Intent
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
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.bottomnav.BottomNavigationActivity
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormCompanyViewModel : ViewModel() {
    val isFormCompLiveData = MutableLiveData<Boolean>()
    val isLocationSpinner = MutableLiveData<List<LocationModel>>()

    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil

    fun setSharedPref(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun setFormCompanyService(service: AuthApiService) {
        this.service = service
    }

    fun callAuthApi(
        Name: RequestBody,
        Field: RequestBody,
        Position: RequestBody,
        Location: RequestBody,
        Description: RequestBody,
        Instagram: RequestBody,
        Phone: RequestBody,
        Linkedin: RequestBody,
        idAcc: RequestBody
    ) {


        service?.companyRequest(
            Name,
            Field,
            Position,
            Location,
            Description,
            Instagram,
            Phone,
            Linkedin,
            idAcc
        )?.enqueue(
            object : Callback<CompanyResponse> {
                override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<CompanyResponse>,
                    response: Response<CompanyResponse>
                ) {
                    isFormCompLiveData.value = true
                }
            }
        )
    }

    fun initSpinnerLoc() {
        service?.getLocation()?.enqueue(object : Callback<LocationResponse> {
            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
//                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                val listLoc = response.body()?.data?.map {
                    LocationModel(
                        it.idLoc.orEmpty(), it.nameLoc.orEmpty()
                    )
                } ?: listOf()
                isLocationSpinner.value = listLoc

            }

        })
    }


}