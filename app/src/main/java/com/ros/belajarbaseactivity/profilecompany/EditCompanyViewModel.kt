package com.ros.belajarbaseactivity.profilecompany

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
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import com.squareup.picasso.Picasso
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCompanyViewModel : ViewModel() {
    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil

    val isPutCompLiveData = MutableLiveData<Boolean>()
    val responseGetCompLiveData = MutableLiveData<CompanyByIDResponse>()
    val isLocationLiveData = MutableLiveData<List<LocationModel>>()

    fun setEditCompanyService(service: AuthApiService) {
        this.service = service
    }

    fun setSharedPref(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun getDataCompany() {
        val idAcc = sharedpref.getString(Constant.PREF_ID_ACC)
        service?.getCompanyID(idAcc)?.enqueue(object : Callback<CompanyByIDResponse> {
            override fun onFailure(call: Call<CompanyByIDResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<CompanyByIDResponse>,
                response: Response<CompanyByIDResponse>
            ) {
                responseGetCompLiveData.value = response.body()
            }
        })
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
        img: MultipartBody.Part?,
        idAcc: RequestBody
    ) {
        val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)
        service?.putCompany(
            idComp, Name, Field, Position, Location, Description, Instagram,
            Phone, Linkedin, img, idAcc
        )?.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isPutCompLiveData.value = true
            }
        })
    }

    fun initSpinnerLoc() {
        service?.getLocation()?.enqueue(object : Callback<LocationResponse> {
            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
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
                isLocationLiveData.value = listLoc
            }

        })
    }


}