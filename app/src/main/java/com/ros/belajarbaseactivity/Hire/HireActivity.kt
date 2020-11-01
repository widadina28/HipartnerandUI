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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    lateinit var binding: ActivityHireBinding
    private lateinit var sharedpref: sharedprefutil
    private lateinit var viewModel: HireViewModel


    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hire)
    }

    override fun onCreateActivity() {
        sharedpref = sharedprefutil(applicationContext)

        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        viewModel = ViewModelProvider(this).get(HireViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)

        if (service != null) {
            viewModel.setHireService(service)
        }

        viewModel.spinnerProject(binding.spinnerHireProject)

        subscribeLiveData()

    }

    private fun subscribeLiveData() {

        viewModel.isProjectSpinnerLiveData.observe(this, Observer {
            var spinner = binding.spinnerHireProject
            spinner.adapter = ArrayAdapter<String>(
                this@HireActivity,
                R.layout.support_simple_spinner_dropdown_item,
                it.map {
                    it.projectName
                })
        })
        viewModel.isHireLiveData.observe(this, Observer {
            if (it) {
                Toast.makeText(this@HireActivity, "Sent!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@HireActivity, "Failed!", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun initListener() {
        binding.btnSubmitHire.setOnClickListener {
            viewModel.callApi(
                binding.etDescHire.text.toString(),
                binding.etPriceHire.text.toString(),
                binding.etDateHire.text.toString()
            )
        }
    }
}