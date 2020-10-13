package com.ros.belajarbaseactivity.register

import android.view.View
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil

interface RegisterContract {
    interface View {
        fun registerSucces()
        fun registerfailed()
    }

    interface Presenter {
        fun bindToView(view: View)
        fun unbind()
        fun callApi(name: String, email:String, password:String)
        fun setSharedPref(sharedpref: sharedprefutil)
    }
}