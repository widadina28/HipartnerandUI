package com.ros.belajarbaseactivity.profilecompany

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.bottomnav.BottomNavigationActivity
import com.ros.belajarbaseactivity.databinding.ActivityFormCompanyBinding
import com.ros.belajarbaseactivity.login.Login
import com.ros.belajarbaseactivity.login.LoginResponse
import com.ros.belajarbaseactivity.register.RegisterResponse
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormCompany : BaseActivity() {
    private lateinit var binding : ActivityFormCompanyBinding
    private lateinit var sharedpref : sharedprefutil
    private var selectedLoc =""

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form_company)
    }

    override fun onCreateActivity() {
        sharedpref = sharedprefutil(applicationContext)
        initSpinnerLoc()
    }

    private fun callAuthApi() {
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        val idAcc = sharedpref.getString(Constant.PREF_ID_ACC)

    service?.companyRequest(createPartFromString(binding.etCompanyNameR.text.toString()), createPartFromString(binding.etCompanyFieldR.text.toString()),
    createPartFromString("HRD"), createPartFromString(selectedLoc), createPartFromString(binding.etCompanyDescriptionR.text.toString()),
    createPartFromString(binding.etCompanyInstagramR.text.toString()), createPartFromString(binding.etCompanyPhoneR.text.toString()),
    createPartFromString(binding.etCompanyLinkedinR.text.toString()),createPartFromString("$idAcc"))?.enqueue(
        object : Callback<CompanyResponse>{
            override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
                Toast.makeText(this@FormCompany, "$t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<CompanyResponse>,
                response: Response<CompanyResponse>
            ) {
                Toast.makeText(this@FormCompany, "Profile Data Sent!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@FormCompany, BottomNavigationActivity::class.java))
                finish()
            }
        }
    )
    }


    private fun initSpinnerLoc(){
        var spinner= binding.spinnerLocR as Spinner
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        service?.getLocation()?.enqueue(object : Callback<LocationResponse> {
            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                Log.d("API data", "Data tidak masuk!")
            }
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                Log.d("API data", "data masuk: ${response.body()}")
                val listLoc = response.body()?.data?.map {
                    LocationModel(it.idLoc.orEmpty(), it.nameLoc.orEmpty()
                    )} ?: listOf()
                spinner.adapter = ArrayAdapter<String>(this@FormCompany, R.layout.support_simple_spinner_dropdown_item, listLoc.map {
                    it.nameLoc
                })
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedLoc = listLoc[position].idLoc.toString()
                    }

                }
            }

            })
        }



    override fun initListener() {
        binding.btnsubmitR.setOnClickListener {
            callAuthApi()
        }

    }
    @NonNull
    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json
            .toRequestBody(mediaType)
    }
}