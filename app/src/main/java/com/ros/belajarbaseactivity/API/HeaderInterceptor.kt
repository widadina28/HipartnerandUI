package com.ros.belajarbaseactivity.API

import android.content.Context
import android.util.Log
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor (val mcontext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val sharepref = sharedprefutil(contex = mcontext)
        val token = sharepref.getString(Constant.PREF_TOKEN)
        Log.d("Token Kamu Kemana", "$token")
        proceed(
            request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        )
    }
}