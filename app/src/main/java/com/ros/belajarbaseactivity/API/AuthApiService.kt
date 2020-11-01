package com.ros.belajarbaseactivity.API

import com.ros.belajarbaseactivity.Hire.HireProjectResponse
import com.ros.belajarbaseactivity.Hire.HireResponse
import com.ros.belajarbaseactivity.Project.PostProjectResponse
import com.ros.belajarbaseactivity.Project.ProjectByIDResponse
import com.ros.belajarbaseactivity.Project.ProjectResponse
import com.ros.belajarbaseactivity.engineer.*
import com.ros.belajarbaseactivity.login.LoginResponse
import com.ros.belajarbaseactivity.profilecompany.CompanyByIDResponse
import com.ros.belajarbaseactivity.profilecompany.CompanyResponse
import com.ros.belajarbaseactivity.profilecompany.LocationResponse
import com.ros.belajarbaseactivity.register.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface AuthApiService {
    @FormUrlEncoded
    @POST("account/login")
    suspend fun loginRequest(
        @Field("email_account") email: String?,
        @Field("password") password: String?
    ): LoginResponse

    @FormUrlEncoded
    @POST("account/register")
    suspend fun registerRequest(
        @Field("name_account") name_account: String?,
        @Field("email_account") email: String?,
        @Field("password") password: String?,
        @Field("role") role: String?
    ): RegisterResponse

    @FormUrlEncoded
    @POST("hire")
    fun postHire(
        @Field("id_project") id_project: String?,
        @Field("id_engineer") id_engineer: String?,
        @Field("description") description: String?,
        @Field("price") price: String?,
        @Field("status") status: String?,
        @Field("confirm_date") confirm_date: String?
    ): Call<HireResponse>

    @Multipart
    @PUT("company/{id}")
    fun putCompany(
        @Path("id") id: String?,
        @Part("name_company") name: RequestBody,
        @Part("field") field: RequestBody,
        @Part("position") position: RequestBody,
        @Part("id_loc") idloc: RequestBody,
        @Part("description_company") description: RequestBody,
        @Part("instagram_company") instagram: RequestBody,
        @Part("telp_company") phone: RequestBody,
        @Part("linkedin_company") likedin: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("idacc") id_account: RequestBody
    ): Call<Void>

    @Multipart
    @PUT("project/{id}")
    fun putProject(
        @Path("id") id: String?,
        @Part("project_name") project_name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("deadline") deadline: RequestBody,
        @Part image: MultipartBody.Part?,
        @Part("id_company") id_company: RequestBody,
        @Part("price") price: RequestBody
    ): Call<Void>

    @Multipart
    @POST("company")
    fun companyRequest(
        @Part("name_company") nameCompany: RequestBody,
        @Part("field") field: RequestBody,
        @Part("position") position: RequestBody,
        @Part("id_loc") idLoc: RequestBody,
        @Part("description_company") descriptionCompany: RequestBody,
        @Part("instagram_company") instagramCompany: RequestBody,
        @Part("telp_company") telpCompany: RequestBody,
        @Part("linkedin_company") linkedinCompany: RequestBody,
        @Part("idacc") idacc: RequestBody
    ): Call<CompanyResponse>

    @Multipart
    @POST("project")
    fun postProject(
        @Part("project_name") project_name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("deadline") deadline: RequestBody,
        @Part("id_company") id_company: RequestBody,
        @Part("price") price: RequestBody,
        @Part image: MultipartBody.Part?
    ): Call<PostProjectResponse>


    @GET("engineer")
    fun getAllEngineer(): Call<EngineerResponse>

    @GET("engineer/{id}")
    fun getEngineerByID(@Path("id") id: String?): Call<EngineerResponseID>

    @GET("experience/{id}")
    fun getExperienceByID(@Path("id") id: String?): Call<ExperienceResponse>

    @GET("company/{id}")
    fun getCompanyID(@Path("id") id: String?): Call<CompanyByIDResponse>

    @GET("portofolio/{id}")
    fun getPortofolioByID(@Path("id") id: String?): Call<PortofolioResponse>

    @GET("project/company/{id}")
    fun getProject(@Path("id") id: String?): Call<ProjectResponse>

    @GET("project/{id}")
    fun getProjectByID(@Path("id") id: Int?): Call<ProjectByIDResponse>

    @GET("location")
    fun getLocation(): Call<LocationResponse>

    @GET("engineer")
    suspend fun getEngineerForSearch(
        @Query("limit") limit: Int?,
        @Query("search") search: String?
    ): EngineerResponse

    @DELETE("project/{id}")
    fun deleteProject(@Path("id") id: String): Call<Void>

    @GET("hire/project/{id}")
    fun getHireProject(@Path("id") id: String?): Call<HireProjectResponse>

}