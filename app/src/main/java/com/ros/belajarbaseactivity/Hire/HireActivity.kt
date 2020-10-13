package com.ros.belajarbaseactivity.Hire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.Project.ProjectModel
import com.ros.belajarbaseactivity.Project.ProjectResponse
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityHireBinding
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.LocalDate

class HireActivity : BaseActivity() {
    lateinit var binding : ActivityHireBinding
    private lateinit var sharedpref: sharedprefutil
    private var selectedProject =""


    override fun initBinding() {
    binding = DataBindingUtil.setContentView(this, R.layout.activity_hire)
    }

    override fun onCreateActivity() {
        sharedpref = sharedprefutil(applicationContext)
        spinnerProject()
    }

    private fun callApi(){
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)

        service?.postHire("$selectedProject", "$idEngineer", binding.etDescHire.text.toString(),
            binding.etPriceHire.text.toString(), "Confirmed", binding.etDateHire.text.toString())
            ?.enqueue(object : Callback<HireResponse>{
            override fun onFailure(call: Call<HireResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                Log.d("API data Hire Post", "Data tidak masuk!")
            }

            override fun onResponse(call: Call<HireResponse>, response: Response<HireResponse>) {
                Log.d("Hire API", "respon hire: ${response.body()}")
                Toast.makeText(this@HireActivity, "Sent!", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun spinnerProject() {
        var spinner=binding.spinnerHireProject as Spinner
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)
        Log.d("idComSpinner", "$idComp")
        service?.getProject(idComp)?.enqueue(object : Callback<ProjectResponse>{
            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                Log.d("API data project", "Data tidak masuk!")
            }
            override fun onResponse(
                call: Call<ProjectResponse>,
                response: Response<ProjectResponse>
            ) {
                Log.d("API data Project", "data masuk: ${response.body()}")
                val listProject = response.body()?.data?.map {
                    ProjectModel(it.idProject.orEmpty(), it.projectName.orEmpty())
                } ?: listOf()
                spinner.adapter = ArrayAdapter<String>(this@HireActivity, R.layout.support_simple_spinner_dropdown_item, listProject.map {
                    it.projectName })
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long)
                    { selectedProject = listProject[position].idProject.toString()
                    }
                }
            }

        })
    }

    override fun initListener() {
        binding.btnSubmitHire.setOnClickListener {
            callApi()
        }
    }
}