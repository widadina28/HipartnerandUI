package com.ros.belajarbaseactivity.engineer

import com.google.gson.annotations.SerializedName

data class PortofolioResponse (val success: String?, val message: String? ,val data:List<DataResult>) {
    data class DataResult(
        @SerializedName("aplication_name")
        val aplicationName: String,
        @SerializedName("id_engineer")
        val idEngineer: String,
        @SerializedName("id_portofolio")
        val idPortofolio: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("link_repo")
        val linkRepo: String,
        @SerializedName("type_porto")
        val typePorto: String
    )
}