package com.ros.belajarbaseactivity.bottomnav

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.databinding.FragmentHomeBinding
import com.ros.belajarbaseactivity.engineer.DetailEngineer
import com.ros.belajarbaseactivity.RecyclerView.EngineerAdapter
import com.ros.belajarbaseactivity.engineer.EngineerModel
import com.ros.belajarbaseactivity.engineer.EngineerResponse
//import com.ros.belajarbaseactivity.RecyclerView.EngineerAdapter
//import com.ros.belajarbaseactivity.engineer.EngineerModel
//import com.ros.belajarbaseactivity.engineer.EngineerResponse
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: sharedprefutil
    private lateinit var viewModel: HomeFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        binding = FragmentHomeBinding.inflate(inflater)
        sharedpref = sharedprefutil(requireContext())
        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setHireService(service)
        }

        rv = binding.recyclerview
        rv.adapter = EngineerAdapter(
            arrayListOf(),
            object :
                EngineerAdapter.OnAdapterListenerEng {
                override fun onClick(engineer: EngineerModel) {
                    sharedpref.putString(Constant.PREF_ID_ENGINEER, engineer.id)
                    val intent = Intent(requireActivity(), DetailEngineer::class.java)
                    startActivity(intent)
                }
            })
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewModel.callApi()
        subscribeLiveData()

        return binding.root
    }

    fun subscribeLiveData() {
        viewModel.isResponseHome.observe(viewLifecycleOwner, Observer {
            (binding.recyclerview.adapter as EngineerAdapter).addList(it)
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }


}