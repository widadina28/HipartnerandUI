package com.ros.belajarbaseactivity.engineer

import android.content.Intent
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
import com.ros.belajarbaseactivity.RecyclerView.PortofolioAdapter
import com.ros.belajarbaseactivity.databinding.FragmentPortofolioBinding
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Response

class PortofolioFragment : Fragment() {
    private lateinit var binding: FragmentPortofolioBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: sharedprefutil
    private lateinit var portofolioAdapter: PortofolioAdapter
    private lateinit var viewModel: PortfolioViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortofolioBinding.inflate(inflater)
        sharedpref = sharedprefutil(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        viewModel = ViewModelProvider(this).get(PortfolioViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setHireService(service)
        }


        rv = binding.rvportofolio
        portofolioAdapter = PortofolioAdapter(
            arrayListOf(), object : PortofolioAdapter.OnAdapterListenerPorto {
                override fun onClick(portofolio: PortofolioModel) {
                    val a = sharedpref.putString(Constant.PREF_LINK_REPO, portofolio.linkRepo)
                    val intent = Intent(requireActivity(), LinkRepo::class.java)
                    startActivity(intent)
                }

            }
        )
        binding.rvportofolio.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = portofolioAdapter
        }
        viewModel.callApi()
        subscribeLiveData()
        return binding.root
    }

    fun subscribeLiveData() {
        viewModel.isResponsePortfolio.observe(viewLifecycleOwner, Observer {
            (binding.rvportofolio.adapter as PortofolioAdapter).addList(it)
        })
    }


}