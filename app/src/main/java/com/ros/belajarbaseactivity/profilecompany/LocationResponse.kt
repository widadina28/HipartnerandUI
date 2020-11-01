package com.ros.belajarbaseactivity.profilecompany

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    val success: String?,
    val message: String,
    val data: List<DataResult>?
) {
    data class DataResult(
        @SerializedName("count")
        val count: Int,
        @SerializedName("id_loc")
        val idLoc: String,
        @SerializedName("name_loc")
        val nameLoc: String
    )
}