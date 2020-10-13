package com.ros.belajarbaseactivity.room


import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.Project.ProjectByIDResponse
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityEditProjectBinding
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_project.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProject : BaseActivity() {

    private lateinit var binding: ActivityEditProjectBinding
    private lateinit var sharedpref: sharedprefutil

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_project)
    }

    override fun onCreateActivity() {
        sharedpref = sharedprefutil(applicationContext)
        setupView()
        setupListener()
    }
    private fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        when (intentType()) {
            ConstantProject.TYPE_CREATE -> {
                supportActionBar!!.title = "Create New Project"
                btnsubmit.visibility = View.VISIBLE
                btnupdate.visibility = View.GONE
            }
            ConstantProject.TYPE_READ -> {
                supportActionBar!!.title = " Read Only "
                btnsubmit.visibility = View.GONE
                btnupdate.visibility = View.GONE
                getProject()
            }
            ConstantProject.TYPE_UPDATE -> {
                supportActionBar!!.title = "Edit"
                btnsubmit.visibility = View.GONE
                btnupdate.visibility = View.VISIBLE
                getProject()
            }
        }
    }

    private fun  setupListener() {
        btnsubmit.setOnClickListener {
            val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
            val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)
//            service?.postProject(binding.etNameProject.text.toString(), binding.etDescriptionPoject.text.toString(),
//            binding.etTimer.text.toString(),"$idComp", binding.etPriceEditProject.text.toString(), //image)


        }
        btnupdate.setOnClickListener {

        }
    }

    private fun getProject(){
        val idProject = sharedpref.getString(Constant.PREF_ID_PROJECT)?.toInt()
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)

        service?.getProjectByID(idProject)?.enqueue(object : Callback<ProjectByIDResponse>{
            override fun onFailure(call: Call<ProjectByIDResponse>, t: Throwable) {
                Log.d("getProject", "eror = $t")
            }

            override fun onResponse(
                call: Call<ProjectByIDResponse>,
                response: Response<ProjectByIDResponse>
            ) {
                binding.etDescriptionPoject.setText(response.body()?.data?.description)
                binding.etNameProject.setText(response.body()?.data?.projectName)
                binding.etPriceEditProject.setText(response.body()?.data?.price)
//                response.body()?.data?.price?.let { binding.etPriceEditProject.setText(it) }
                Picasso.get().load("http://3.80.45.131:8080/uploads/" + response.body()?.data?.image).placeholder(R.drawable.ic_image_editing).into(binding.cvImgEditProject)
                binding.etTimer.setText(response.body()?.data?.deadline)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun intentType(): Int {
        return intent.getIntExtra("intent_type", 0)
    }

    override fun initListener() {}

    private fun image(){

    }
}