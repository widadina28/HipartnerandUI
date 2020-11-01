package com.ros.belajarbaseactivity.engineer

import com.google.gson.annotations.SerializedName

data class EngineerResponse(
    val success: String?,
    val message: String?,
    val data: List<DataResult>
) {
    data class DataResult(
        @SerializedName("cost")
        val cost: Int,
        @SerializedName("count")
        val count: Int,
        @SerializedName("id_engineer")
        val idEngineer: String?,
        @SerializedName("image")
        val image: String,
        @SerializedName("name_engineer")
        val nameEngineer: String,
        @SerializedName("name_freelance")
        val nameFreelance: String,
        @SerializedName("name_loc")
        val nameLoc: String,
        @SerializedName("name_skill")
        val nameSkill: String,
        @SerializedName("rate")
        val rate: String,
        @SerializedName("status")
        val status: String
    )
}