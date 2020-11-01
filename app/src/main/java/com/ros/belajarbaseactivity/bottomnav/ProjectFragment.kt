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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var binding: FragmentProjectBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: sharedprefutil
    private lateinit var viewModel: ProjectFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectBinding.inflate(inflater)
        sharedpref = sharedprefutil(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        viewModel = ViewModelProvider(this).get(ProjectFragmentViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setHireService(service)
        }


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
        viewModel.callApi()
        subscribeLiveData()

        return binding.root
    }

    private fun subscribeLiveData() {
        viewModel.isResponseProject.observe(viewLifecycleOwner, Observer {
            (binding.recyclv.adapter as ProjectAdapter).setData(it)
        })
        viewModel.isDeleteProject.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Delete Success", Toast.LENGTH_SHORT).show()
            viewModel.callApi()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.callApi()
    }


    private fun intentEdit(intent_type: Int) {
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

    private fun deleteAlert(project: ProjectAllModel) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.apply {
            setTitle("Delete Confirm")
            setMessage("Are You Sure to Delete ${project.projectName}?")
            setNegativeButton("Cancel") { dialog: DialogInterface?, which: Int ->
                dialog?.dismiss()
            }
            setPositiveButton("Delete") { dialog: DialogInterface?, which: Int ->
                viewModel.delete(project.idProject)
                dialog?.dismiss()
            }
        }
        dialog.show()
    }

}
