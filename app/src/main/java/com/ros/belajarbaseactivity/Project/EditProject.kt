package com.ros.belajarbaseactivity.Project


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.ActivityEditProjectBinding
import com.ros.belajarbaseactivity.profilecompany.EditCompany
import com.ros.belajarbaseactivity.profilecompany.EditCompany.Companion.PERMISSION_CODE
import com.ros.belajarbaseactivity.room.ConstantProject
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_project.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditProject : BaseActivity() {

    private lateinit var binding: ActivityEditProjectBinding
    private lateinit var sharedpref: sharedprefutil
    private var img: MultipartBody.Part? = null
    companion object {
        private val IMAGE_PICK_CODE = 1000
        val PERMISSION_CODE = 1001
    }

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
                binding.btnsubmit.visibility = View.VISIBLE
                binding.btnupdate.visibility = View.GONE
            }
            ConstantProject.TYPE_READ -> {
                supportActionBar!!.title = " Read Only "
                binding.btnsubmit.visibility = View.GONE
                binding.btnupdate.visibility = View.GONE
                binding.editimgBtnProject.visibility = View.GONE
                getProject()
            }
            ConstantProject.TYPE_UPDATE -> {
                supportActionBar!!.title = "Edit"
                binding.btnsubmit.visibility = View.GONE
                binding.btnupdate.visibility = View.VISIBLE
                getProject()
            }
        }
    }

    private fun  setupListener() {

        btnsubmit.setOnClickListener {
            val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)
            val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
            service?.postProject(createPartFromString(binding.etNameProject.text.toString()),
            createPartFromString(binding.etDescriptionPoject.text.toString()),
            createPartFromString(binding.etTimer.text.toString()),
            createPartFromString("$idComp"), createPartFromString(binding.etPriceEditProject.text.toString()),
            img)?.enqueue(object : Callback<PostProjectResponse>{
                override fun onFailure(call: Call<PostProjectResponse>, t: Throwable) {
                    Toast.makeText(this@EditProject, "$t", Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(
                    call: Call<PostProjectResponse>,
                    response: Response<PostProjectResponse>
                ) {
                    Toast.makeText(this@EditProject,"Submit Success!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })


        }
        btnupdate.setOnClickListener {
            val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)
            val idProject = sharedpref.getString(Constant.PREF_ID_PROJECT)
            val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
            Log.d("idProjectEdit", "id:$idProject")
            val a = createPartFromString(binding.etNameProject.text.toString())
            val b = createPartFromString(binding.etDescriptionPoject.text.toString())
            val c = createPartFromString(binding.etTimer.text.toString())
            val d = img
            val e = createPartFromString("$idComp")
            val f =createPartFromString(binding.etPriceEditProject.text.toString())

            service?.putProject(idProject, a, b, c, d, e, f)?.enqueue(object :Callback<Void>{
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@EditProject, "$t", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.d("putProjecr", "${response.body()}")
                    Toast.makeText(this@EditProject,"Update Success!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
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

    override fun initListener(
    ) {
        binding.editimgBtnProject.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, EditProject.PERMISSION_CODE)
                }
                else {
                    pickImgGallery()
                }
            } else {
                pickImgGallery()
            }
        }
        }
    private fun pickImgGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, EditProject.IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            EditProject.PERMISSION_CODE -> {
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
        if (resultCode == Activity.RESULT_OK && requestCode == EditProject.IMAGE_PICK_CODE) {
            binding.cvImgEditProject.setImageURI(data?.data)
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