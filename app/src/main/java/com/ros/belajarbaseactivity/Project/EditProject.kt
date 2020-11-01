package com.ros.belajarbaseactivity.Project


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.BaseActivity
import com.ros.belajarbaseactivity.Hire.EngineerInProjectModel
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.RecyclerView.EngineerInProjectAdapter
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
    private lateinit var viewModel: EditProjectViewModel
    private lateinit var rv: RecyclerView


    private var img: MultipartBody.Part? = null

    companion object {
        private val IMAGE_PICK_CODE = 1000
        val PERMISSION_CODE = 1001
    }

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_project)
    }

    override fun onCreateActivity() {
        val service = ApiClient.getApiClient(this)?.create(AuthApiService::class.java)
        viewModel = ViewModelProvider(this).get(EditProjectViewModel::class.java)
        sharedpref = sharedprefutil(applicationContext)

        if (service != null) {
            viewModel.setEditProjectService(service)
        }

        viewModel.setSharedPref(sharedpref)

        rv = binding.recyclerEngineerProject
        rv.adapter = EngineerInProjectAdapter(arrayListOf())
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        setupView()
        setupListener()
        subscribeLiveData()

    }


    private fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        when (intentType()) {
            ConstantProject.TYPE_CREATE -> {
                supportActionBar!!.title = "Create New Project"
                binding.btnsubmit.visibility = View.VISIBLE
                binding.btnupdate.visibility = View.GONE
                binding.engineerInProject.visibility = View.GONE
            }
            ConstantProject.TYPE_READ -> {
                supportActionBar!!.title = " Read Only "
                binding.btnsubmit.visibility = View.GONE
                binding.btnupdate.visibility = View.GONE
                binding.editimgBtnProject.visibility = View.GONE
                viewModel.getProject()
                viewModel.getHireProject()
            }
            ConstantProject.TYPE_UPDATE -> {
                supportActionBar!!.title = "Edit"
                binding.btnsubmit.visibility = View.GONE
                binding.btnupdate.visibility = View.VISIBLE
                viewModel.getProject()
            }
        }
    }

    private fun setupListener() {

        btnsubmit.setOnClickListener {
            val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)
            viewModel.postProject(
                createPartFromString(binding.etNameProject.text.toString()),
                createPartFromString(binding.etDescriptionPoject.text.toString()),
                createPartFromString(binding.etTimer.text.toString()),
                createPartFromString("$idComp"),
                createPartFromString(binding.etPriceEditProject.text.toString()),
                img
            )
        }
        btnupdate.setOnClickListener {
            val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)
            viewModel.putProject(
                createPartFromString(binding.etNameProject.text.toString()),
                createPartFromString(binding.etDescriptionPoject.text.toString()),
                createPartFromString(binding.etTimer.text.toString()),
                img,
                createPartFromString("$idComp"),
                createPartFromString(binding.etPriceEditProject.text.toString())
            )
        }
    }

    fun subscribeLiveData() {
        viewModel.responseGetProjectLiveData.observe(this, Observer {
            binding.etDescriptionPoject.setText(it.data?.description)
            binding.etNameProject.setText(it.data?.projectName)
            binding.etPriceEditProject.setText(it.data?.price)
            Picasso.get().load("http://3.80.45.131:8080/uploads/" + it.data?.image).placeholder(
                R.drawable.ic_image_editing
            ).into(binding.cvImgEditProject)
            binding.etTimer.setText(it.data?.deadline)
        })
        viewModel.responsePostProjectLiveData.observe(this, Observer {
            Toast.makeText(this@EditProject, "Submit Success!", Toast.LENGTH_SHORT).show()
            finish()
        })
        viewModel.responsePutProjectLiveData.observe(this, Observer {
            if (it) {
                Toast.makeText(this@EditProject, "Update Success!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@EditProject, "Update Failed!", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.responseGetHireProject.observe(this, Observer {
            val List = it.data.map {
                EngineerInProjectModel(it.nameEngineer.orEmpty(), it.status.orEmpty())
            } ?: listOf()
            (binding.recyclerEngineerProject.adapter as EngineerInProjectAdapter).addList(List)
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
                } else {
                    pickImgGallery()
                }
            } else {
                pickImgGallery()
            }
        }
    }

    private fun pickImgGallery() {
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
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImgGallery()
                } else {
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
                MultipartBody.Part.createFormData(
                    "image", file.name,
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