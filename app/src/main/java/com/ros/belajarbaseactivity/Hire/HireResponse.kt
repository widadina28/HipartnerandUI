package com.ros.belajarbaseactivity.Hire

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*

data class HireResponse (val success:String?, val message: String, val data:DataResult) {
    data class DataResult(
        @SerializedName("confirm_date")
        val confirmDate: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("id_engineer")
        val idEngineer: String,
        @SerializedName("id_project")
        val idProject: String,
        @SerializedName("price")
        val price: String,
        @SerializedName("status")
        val status: String
    )
}