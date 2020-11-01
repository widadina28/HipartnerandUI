package com.ros.belajarbaseactivity.engineer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.RecyclerView.ExperienceAdapter
import com.ros.belajarbaseactivity.databinding.FragmentExperienceBinding
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ExperienceFragment : Fragment() {
    private lateinit var binding: FragmentExperienceBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: sharedprefutil
    private lateinit var viewModel: ExperienceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExperienceBinding.inflate(inflater)
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        sharedpref = sharedprefutil(requireContext())
        viewModel = ViewModelProvider(this).get(ExperienceViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setHireService(service)
        }

        rv = binding.rvexperience
        rv.adapter = ExperienceAdapter(arrayListOf())
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewModel.callApi()
        subscribeLiveData()

        return binding.root
    }

    fun subscribeLiveData() {
        viewModel.isResponseExperience.observe(viewLifecycleOwner, Observer {
            (binding.rvexperience.adapter as ExperienceAdapter).addList(it)
        })
    }


}