package com.ros.belajarbaseactivity.bottomnav

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.RecyclerView.EngineerAdapter
import com.ros.belajarbaseactivity.databinding.FragmentOffersBinding
import com.ros.belajarbaseactivity.engineer.DetailEngineer
import com.ros.belajarbaseactivity.engineer.EngineerModel
import com.ros.belajarbaseactivity.search.SearchViewModel
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentOffersBinding
    private lateinit var rv: RecyclerView
    private lateinit var sv: SearchView
    private lateinit var viewModel: SearchViewModel

    private lateinit var sharedpref: sharedprefutil
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOffersBinding.inflate(inflater)
        sharedpref = sharedprefutil(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setServieSearch(service)
        }
        sv = binding.etSearchBox
        rv = binding.recyclerviewSearch
        rv.adapter = EngineerAdapter(arrayListOf(),
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

    private fun subscribeLiveData() {
        viewModel.responseGetAllEngineer.observe(viewLifecycleOwner, Observer {
            (binding.recyclerviewSearch.adapter as EngineerAdapter).addList(it)
            sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    var recentSearch: ArrayList<EngineerModel> = ArrayList()
                    for (data in it) {
                        if (data.nameEngineer.contains(query) || data.nameLoc.contains(query) || data.rate.contains(
                                query
                            ) ||
                            data.status.contains(query) || data.nameSkill.contains(query)
                        ) {
                            recentSearch.add(data)
                        }
                    }
                    (binding.recyclerviewSearch.adapter as EngineerAdapter).addList(recentSearch)
                    return false

                }

                override fun onQueryTextChange(newText: String): Boolean {
                    var recentSearch: ArrayList<EngineerModel> = ArrayList()
                    for (data in it) {
                        if (data.nameEngineer.contains(newText) || data.nameLoc.contains(newText) || data.rate.contains(
                                newText
                            ) ||
                            data.status.contains(newText) || data.nameSkill.contains(newText)
                        ) {
                            recentSearch.add(data)
                        }
                    }
                    (binding.recyclerviewSearch.adapter as EngineerAdapter).addList(recentSearch)
                    return false
                }

            })
        })
    }


}

