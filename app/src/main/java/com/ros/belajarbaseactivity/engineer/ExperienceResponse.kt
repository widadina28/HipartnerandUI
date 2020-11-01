package com.ros.belajarbaseactivity.engineer

import com.google.gson.annotations.SerializedName

data class ExperienceResponse(
    val success: String?,
    val message: String?,
    val data: List<DataResult>
) {
    data class DataResult(
        @SerializedName("company_name")
        val companyName: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("end")
        val end: String?,
        @SerializedName("id_engineer")
        val idEngineer: String?,
        @SerializedName("id_experience")
        val idExperience: String?,
        @SerializedName("position")
        val position: String?,
        @SerializedName("start")
        val start: String?
    )
}