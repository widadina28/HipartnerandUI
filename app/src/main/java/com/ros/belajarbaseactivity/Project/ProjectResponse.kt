package com.ros.belajarbaseactivity.Project

import com.google.gson.annotations.SerializedName

data class ProjectResponse(val success: String?, val message: String, val data: List<DataResult>?) {
    data class DataResult(
        @SerializedName("count")
        val count: Int,
        @SerializedName("createAt")
        val createAt: String,
        @SerializedName("deadline")
        val deadline: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id_project")
        val idProject: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("name_company")
        val nameCompany: String,
        @SerializedName("price")
        val price: Int,
        @SerializedName("project_name")
        val projectName: String,
        @SerializedName("updateAt")
        val updateAt: String
    )
}