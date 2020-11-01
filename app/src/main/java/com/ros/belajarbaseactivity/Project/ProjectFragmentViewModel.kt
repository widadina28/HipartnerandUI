package com.ros.belajarbaseactivity.Project

import androidx.lifecycle.ViewModel
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil

class ProjectFragmentViewModel : ViewModel() {
    private lateinit var service: AuthApiService
    private lateinit var sharedpref: sharedprefutil

    fun setSharedpreference(sharedpref: sharedprefutil) {
        this.sharedpref = sharedpref
    }

    fun setServiceProjectFragment(service: AuthApiService) {
        this.service = service
    }


}