package com.ros.belajarbaseactivity.Project

import com.google.gson.annotations.SerializedName

data class PostProjectResponse(val success: String?, val message: String, val data: DataResult?) {
    data class DataResult(
        @SerializedName("deadline")
        val deadline: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("id_company")
        val idCompany: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("project_name")
        val projectName: String
    )
}
