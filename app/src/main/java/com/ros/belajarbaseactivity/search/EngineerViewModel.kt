package com.ros.belajarbaseactivity.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.engineer.EngineerModel
import com.ros.belajarbaseactivity.engineer.EngineerResponse
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EngineerViewModel :ViewModel(), CoroutineScope {
    val isLoadingLiveData = MutableLiveData<Boolean>()
    val engineerListLiveData = MutableLiveData<List<EngineerModel>>()

    private lateinit var service : AuthApiService


    override val coroutineContext: CoroutineContext
    get() = Job() + Dispatchers.Main

    fun setEngineerService(service: AuthApiService) {
        this.service = service
    }

    fun getEngineerList(search: String?){
        launch {
            isLoadingLiveData.value = true

            val response = withContext(Dispatchers.IO){
                try {
                    service?.getEngineerForSearch(100, search)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is EngineerResponse){
                val list = response.data?.map {
                    EngineerModel(it.idEngineer.orEmpty(), it.nameEngineer.orEmpty(),
                    it.nameSkill.orEmpty(), it.status.orEmpty(), it.nameLoc.orEmpty(),
                    it.rate.orEmpty(), it.image.orEmpty())
                }
                engineerListLiveData.value = list
            }
            isLoadingLiveData.value = false
        }
    }
}