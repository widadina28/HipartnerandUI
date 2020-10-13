package com.ros.belajarbaseactivity.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse (val success: String?, val message: String, val data: DataResult) {

    data class DataResult(
        @SerializedName("id") val id : String,
        @SerializedName("name_account") val name : String?,
        @SerializedName("email_account") val email : String?,
        @SerializedName("role") val role : String?,
        @SerializedName("createAT") val createAt : String?
    )
}