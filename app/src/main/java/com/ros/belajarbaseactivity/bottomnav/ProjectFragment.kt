package com.ros.belajarbaseactivity.bottomnav

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.Project.ProjectAllModel
import com.ros.belajarbaseactivity.Project.ProjectResponse
import com.ros.belajarbaseactivity.RecyclerView.ProjectAdapter
import com.ros.belajarbaseactivity.databinding.FragmentProjectBinding
import com.ros.belajarbaseactivity.room.ConstantProject
import com.ros.belajarbaseactivity.Project.EditProject
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Response

class ProjectFragment : Fragment() {
    private lateinit var binding :FragmentProjectBinding
    private lateinit var rv : RecyclerView
    private lateinit var sharedpref: sharedprefutil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectBinding.inflate(inflater)
        sharedpref = sharedprefutil(requireContext())

        setUpListener()

        rv = binding.recyclv
        rv.adapter = ProjectAdapter(
            arrayListOf(), object :
        ProjectAdapter.OnAdapterListener {
                override fun onClick(project: ProjectAllModel) {
                    sharedpref.putString(Constant.PREF_ID_PROJECT, project.idProject)
                    intentEdit(ConstantProject.TYPE_READ)
                }

                override fun onUpdate(project: ProjectAllModel) {
                    sharedpref.putString(Constant.PREF_ID_PROJECT, project.idProject)
                    intentEdit(ConstantProject.TYPE_UPDATE)
                }

                override fun onDelete(project: ProjectAllModel) {
                    deleteAlert(project)
                }

            }
        )
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        callApi()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        callApi()
    }

    private fun callApi(){
        val idComp = sharedpref.getString(Constant.PREF_ID_COMPANY)
        Log.d("Project Fragment", "id comp: $idComp")
    val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        service?.getProject(idComp)?.enqueue(object : retrofit2.Callback<ProjectResponse>{
            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
                Log.e("ProjectFragment", t.message ?: "error")
            }
            override fun onResponse(
                call: Call<ProjectResponse>,
                response: Response<ProjectResponse>
            ) {
                Log.e("ProjectFragment",  "sukses!:${response.body()}")
                val list = response.body()?.data?.map {
                    ProjectAllModel(it.idProject.orEmpty(), it.image.orEmpty(), it.projectName.orEmpty(),
                        it.deadline.orEmpty())} ?: listOf()
                Log.d("Project List", "$list")
                    (binding.recyclv.adapter as ProjectAdapter).setData(list)
            }

        })
    }

    private fun intentEdit(intent_type: Int){
            startActivity(
            Intent(requireContext(), EditProject::class.java)
                .putExtra("intent_type", intent_type)
        )
    }

    private fun setUpListener() {
        binding.add.setOnClickListener {
           intentEdit(ConstantProject.TYPE_CREATE)
        }
    }

    private fun deleteAlert(project: ProjectAllModel){
        val dialog = AlertDialog.Builder(requireContext())
        dialog.apply {
            setTitle("Delete Confirm")
            setMessage("Are You Sure to Delete ${project.projectName}?")
            setNegativeButton("Cancel") {
                dialog: DialogInterface?, which: Int ->  dialog?.dismiss()
            }
            setPositiveButton("Delete") { dialog: DialogInterface?, which: Int ->
                val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
                service?.deleteProject(project.idProject)?.enqueue( object : retrofit2.Callback<Void>{
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                    }
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        dialog?.dismiss()
                        Toast.makeText(requireContext(), "Delete Success", Toast.LENGTH_SHORT).show()
                        callApi()
                    }

                })
            }
        }
        dialog.show()
    }

}

//    private lateinit var binding :FragmentProjectBinding
//    private val db by lazy {
//        ProjectDB(requireContext())
//    }
//    private lateinit var projectAdapter: ProjectAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentProjectBinding.inflate(inflater)
//
//        setUpListener()
//        setUpRecyclerView()
//
//        return binding.root
//    }
//
//    override fun onResume() {
//        super.onResume()
//        loadData()
//    }
//
//    private fun loadData() {
//        CoroutineScope(Dispatchers.IO).launch {
//            projectAdapter.setData(db.dao().getdcproject())
//            withContext(Dispatchers.Main) {
//                projectAdapter.notifyDataSetChanged()
//            }
//        }
//    }
//
//    private fun setUpListener() {
//        binding.add.setOnClickListener {
//           intentEdit(ConstantProject.TYPE_CREATE, 0)
//        }
//    }
//
//    private fun setUpRecyclerView(){
//        projectAdapter= ProjectAdapter(
//            arrayListOf(),
//            object : ProjectAdapter.OnAdapterListener{
//            override fun onClick(project: DataClassProject) {
//                intentEdit(ConstantProject.TYPE_READ, project.id)
//            }
//
//            override fun onUpdate(project: DataClassProject) {
//                intentEdit(ConstantProject.TYPE_UPDATE, project.id)
//            }
//
//            override fun onDelete(project: DataClassProject) {
//                deleteAlert(project)
//            }
//        })
//
//        binding.recyclv.apply {
//            layoutManager=LinearLayoutManager(requireActivity())
//            adapter= projectAdapter
//        }
//    }
//
//    private fun intentEdit(intent_type: Int, projectid: Int){
//        startActivity(
//            Intent(requireContext(), EditProject::class.java)
//                .putExtra("intent_type", intent_type)
//                .putExtra("project_id", projectid)
//        )
//    }
//
//    private fun deleteAlert(project: DataClassProject){
//        val dialog = AlertDialog.Builder(requireContext())
//        dialog.apply {
//            setTitle("Delete Confirm")
//            setMessage("Are You Sure to Delete ${project.name}?")
//            setNegativeButton("Cancel") {
//                dialog: DialogInterface?, which: Int ->  dialog?.dismiss()
//            }
//            setPositiveButton("Delete") { dialog: DialogInterface?, which: Int ->
//                CoroutineScope(Dispatchers.IO).launch {
//                    db.dao().delete(project)
//                    dialog?.dismiss()
//                    loadData()
//                }
//            }
//        }
//        dialog.show()
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        (requireActivity() as AppCompatActivity).supportActionBar?.show()
//    }
//}