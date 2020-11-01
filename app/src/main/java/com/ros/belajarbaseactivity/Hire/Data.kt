package com.ros.belajarbaseactivity.Hire


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("name_engineer")
    val nameEngineer: String,
    @SerializedName("status")
    val status: String
)