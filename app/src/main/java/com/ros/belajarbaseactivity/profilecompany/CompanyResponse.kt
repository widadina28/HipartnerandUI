package com.ros.belajarbaseactivity.profilecompany

import com.google.gson.annotations.SerializedName

data class CompanyResponse(val success: String?, val message: String, val data: DataResult?) {
    data class DataResult(
        @SerializedName("description_company")
        val descriptionCompany: String,
        @SerializedName("field")
        val `field`: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("id_loc")
        val idLoc: String,
        @SerializedName("idacc")
        val idacc: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("instagram_company")
        val instagramCompany: String,
        @SerializedName("linkedin_company")
        val linkedinCompany: String,
        @SerializedName("name_company")
        val nameCompany: String,
        @SerializedName("position")
        val position: String,
        @SerializedName("telp_company")
        val telpCompany: String
    )
}