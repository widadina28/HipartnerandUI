package com.ros.belajarbaseactivity.profilecompany

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.bottomnav.BottomNavigationActivity
import com.ros.belajarbaseactivity.bottomnav.ProfileFragment
import com.ros.belajarbaseactivity.databinding.ActivityEditCompanyBinding
import com.ros.belajarbaseactivity.login.LoginResponse
import com.ros.belajarbaseactivity.register.RegisterResponse
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_company.*
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.io.File

class EditCompany : BaseActivity() {
    private lateinit var binding : ActivityEditCompanyBinding
    private lateinit var sharedpref : sharedprefutil
    private var location : ArrayList<LocationResponse.DataResult> = ArrayList()
    private var selectedLoc =""
    private var img: MultipartBody.Part? = null
    companion object {
        private val IMAGE_PICK_CODE = 1000
        val PERMISSION_CODE = 1001
    }

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_company)
    }

    override fun onCreateActivity() {
        sharedpref = sharedprefutil(applicationContext)
        getDataCompany()
        initSpinnerLoc()
    }

    private fun getDataCompany(){
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        val idAcc = sharedpref.getString(Constant.PREF_ID_ACC)
        service?.getCompanyID(idAcc)?.enqueue(object : Callback<CompanyByIDResponse>{
            override fun onFailure(call: Call<CompanyByIDResponse>, t: Throwable) {
                Log.d("Profile Fragment", "error: $t")
            }

            override fun onResponse(
                call: Call<CompanyByIDResponse>,
                response: Response<CompanyByIDResponse>
            ) {
                Log.d("Edit company", "${response.body()}")
                binding.etCompanyName.setText(response.body()?.data?.nameCompany)
                binding.etCompanyField.setText(response.body()?.data?.field)
                binding.etCompanyDescription.setText(response.body()?.data?.descriptionCompany)
                binding.etCompanyLinkedin.setText(response.body()?.data?.linkedinCompany)
                binding.etCompanyInstagram.setText(response.body()?.data?.instagramCompany)
                binding.etCompanyPhone.setText(response.body()?.data?.telpCompany)
                Picasso.get().load("http://3.80.45.131:8080/uploads/" + response.body()?.data?.image).placeholder(R.drawable.ic_baseline_person_24).into(binding.imgprofile)
            }
        })
    }

    private fun callAuthApi(){
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        val idAcc = sharedpref.getString(Constant.PREF_ID_ACC)
        val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)
        Log.d("Edit Comp","idcom:$idComp")
        service?.putCompany(idComp, createPartFromString(binding.etCompanyName.text.toString()),
            createPartFromString(binding.etCompanyField.text.toString()), createPartFromString("HRD"), createPartFromString(selectedLoc),
            createPartFromString(binding.etCompanyDescription.text.toString()), createPartFromString(binding.etCompanyInstagram.text.toString()),
            createPartFromString(binding.etCompanyPhone.text.toString()), createPartFromString(binding.etCompanyLinkedin.text.toString()), img,
            createPartFromString("$idAcc"))?.enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("Edit Company", "error: $t")
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@EditCompany,"Update Success!", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun initSpinnerLoc(){
        var spinner= binding.spinnerLoc as Spinner
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        service?.getLocation()?.enqueue(object : Callback<LocationResponse> {
            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                val listLoc = response.body()?.data?.map {
                    LocationModel(it.idLoc.orEmpty(), it.nameLoc.orEmpty()
                    )} ?: listOf()
                spinner.adapter = ArrayAdapter<String>(this@EditCompany, R.layout.support_simple_spinner_dropdown_item, listLoc.map {
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
    binding.editimgBtn.setOnClickListener {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else {
                pickImgGallery()
            }
        } else {
            pickImgGallery()
        }
    }
    binding.btnSubmitEditCompany.setOnClickListener {
        callAuthApi()
    }
}
    private fun pickImgGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED) {
                    pickImgGallery()
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.imgprofile.setImageURI(data?.data)
            val filePath = getPath(this, data?.data)
            val file = File(filePath)

            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = contentResolver.openInputStream(data?.data!!)
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)
            img = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData("image", file.name,
                    it1
                )
            }


        }
    }

    fun getPath(context: Context, uri: Uri?): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            uri?.let { context.contentResolver.query(it, proj, null, null, null) }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    @NonNull
    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json
            .toRequestBody(mediaType)
    }
}