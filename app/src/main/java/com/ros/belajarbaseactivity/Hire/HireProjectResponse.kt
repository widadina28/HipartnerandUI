package com.ros.belajarbaseactivity.Hire


import com.google.gson.annotations.SerializedName

data class HireProjectResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)