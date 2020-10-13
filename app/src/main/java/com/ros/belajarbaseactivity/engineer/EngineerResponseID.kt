package com.ros.belajarbaseactivity.engineer


import com.google.gson.annotations.SerializedName

data class EngineerResponseID (val success: String?, val message: String? ,val data:DataResult) {
    data class DataResult(
        @SerializedName("cost")
        val cost: Int,
        @SerializedName("count")
        val count: Int,
        @SerializedName("description_engineer")
        val descriptionEngineer: String,
        @SerializedName("id")
        val id: String,
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