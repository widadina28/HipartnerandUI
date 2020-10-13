package com.ros.belajarbaseactivity.Project

import com.google.gson.annotations.SerializedName

data class ProjectByIDResponse(val success: String?, val message: String, val data: DataResult?) {
    data class DataResult (
        @SerializedName("createAt")
        val createAt: String,
        @SerializedName("deadline")
        val deadline: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id_company")
        val idCompany: Int,
        @SerializedName("id_project")
        val idProject: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("project_name")
        val projectName: String,
        @SerializedName("updateAt")
        val updateAt: String
    )
}
