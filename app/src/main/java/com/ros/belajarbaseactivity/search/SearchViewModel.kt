package com.ros.belajarbaseactivity.search

import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.RecyclerView.EngineerAdapter
import com.ros.belajarbaseactivity.bottomnav.HomeFragment
import com.ros.belajarbaseactivity.engineer.EngineerModel
import com.ros.belajarbaseactivity.engineer.EngineerResponse
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class SearchViewModel : ViewModel() {
    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil

    val responseGetAllEngineer = MutableLiveData<List<EngineerModel>>()

    fun setSharedPreference(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun setServieSearch(service: AuthApiService) {
        this.service = service
    }

    fun callApi() {

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
                responseGetAllEngineer.value = list
            }
        })

    }
}