package com.ros.belajarbaseactivity.profilecompany

import com.google.gson.annotations.SerializedName

data class CompanyByIDResponse(val success: String?, val message: String, val data: DataResult?) {
    data class DataResult(
        @SerializedName("createAt")
        val createAt: String,
        @SerializedName("description_company")
        val descriptionCompany: String,
        @SerializedName("email_account")
        val emailAccount: String,
        @SerializedName("field")
        val `field`: String,
        @SerializedName("id_company")
        val idCompany: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("instagram_company")
        val instagramCompany: String,
        @SerializedName("linkedin_company")
        val linkedinCompany: String,
        @SerializedName("name_company")
        val nameCompany: String,
        @SerializedName("name_loc")
        val nameLoc: String,
        @SerializedName("position")
        val position: String,
        @SerializedName("telp_company")
        val telpCompany: String,
        @SerializedName("updateAt")
        val updateAt: String
    )
}