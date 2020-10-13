package com.ros.belajarbaseactivity.login

import com.google.gson.annotations.SerializedName

data class LoginResponse (val success: String?, val message: String, val data: DataResult?) {

data class DataResult(
    @SerializedName("id_account") val id: String,
    @SerializedName("name_account") val name: String,
    @SerializedName("email_account") val email: String?,
    @SerializedName("role") val role: String?,
    @SerializedName("token") val token: String
)

}