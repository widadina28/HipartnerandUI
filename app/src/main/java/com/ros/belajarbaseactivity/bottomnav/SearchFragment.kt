package com.ros.belajarbaseactivity.bottomnav

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.RecyclerView.EngineerAdapter
import com.ros.belajarbaseactivity.databinding.FragmentOffersBinding
import com.ros.belajarbaseactivity.engineer.DetailEngineer
import com.ros.belajarbaseactivity.engineer.EngineerModel
import com.ros.belajarbaseactivity.engineer.EngineerResponse
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Response

class SearchFragment : Fragment(){
        private lateinit var binding :FragmentOffersBinding
    private lateinit var rv : RecyclerView
    private lateinit var sv : SearchView

    private lateinit var sharedpref: sharedprefutil
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOffersBinding.inflate(inflater)
        sharedpref = sharedprefutil(requireContext())
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
        rv.layoutManager= LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        callApi()
        return binding.root
    }

    private fun callApi () {
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        val TAG = HomeFragment::class.java

        service?.getAllEngineer()?.enqueue(object : retrofit2.Callback<EngineerResponse> {
            override fun onFailure(call: Call<EngineerResponse>, t: Throwable) {
                Log.e("TAG", t.message ?: "error")
            }

            override fun onResponse(
                call: Call<EngineerResponse>,
                response: Response<EngineerResponse>
            ) {
                val list = response.body()?.data?.map {
                    EngineerModel(it.idEngineer.orEmpty(), it.nameEngineer.orEmpty(),
                        it.nameSkill.orEmpty(), it.status.orEmpty(), it.nameLoc.orEmpty(), it.rate.orEmpty(),
                        it.image.orEmpty()
                    )} ?: listOf()
                (binding.recyclerviewSearch.adapter as EngineerAdapter).addList(list)
                Log.d("List isinya", "ini $list")
                sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        var recentSearch: ArrayList<EngineerModel> = ArrayList()
                        for (data in list) {
                            if (data.nameEngineer.contains(query) || data.nameLoc.contains(query) || data.rate.contains(query) ||
                                data.status.contains(query) || data.nameSkill.contains(query)) {
                                recentSearch.add(data) }
                        }
                        (binding.recyclerviewSearch.adapter as EngineerAdapter).addList(recentSearch)
                        return false

                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        var recentSearch: ArrayList<EngineerModel> = ArrayList()
                        for (data in list) {
                            if (data.nameEngineer.contains(newText) || data.nameLoc.contains(newText) || data.rate.contains(newText) ||
                                data.status.contains(newText) || data.nameSkill.contains(newText)) {
                                recentSearch.add(data) }
                        }
                        (binding.recyclerviewSearch.adapter as EngineerAdapter).addList(recentSearch)
                        return false
                    }

                })
            }
        })

    }
}

//class SearchFragment : Fragment() {
//    private lateinit var binding :FragmentOffersBinding
//    private lateinit var viewModel: EngineerViewModel
//    private lateinit var sharedpref: sharedprefutil
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentOffersBinding.inflate(inflater)
//        sharedpref = sharedprefutil(requireContext())
//
//        val listenAdapter = object :
//            EngineerAdapter.OnAdapterListenerEng {
//            override fun onClick(engineer: EngineerModel) {
//                sharedpref.putString(Constant.PREF_ID_ENGINEER, engineer.id)
//                val intent = Intent(requireActivity(), DetailEngineer::class.java)
//                startActivity(intent)
//            }
//        }
//
//        binding.recyclerviewSearch.adapter = EngineerAdapter(arrayListOf(),listenAdapter)
//        binding.recyclerviewSearch.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false )
//
//        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
//
//        viewModel = ViewModelProvider(this).get(EngineerViewModel::class.java)
//
//        subscribeLiveData()
//
//        if (service != null){
//            viewModel.setEngineerService(service)
//        }
//
//        binding.etSearchBox.addTextChangedListener(object : TextWatcher{
//            private var searchFor =""
//
//            override fun afterTextChanged(s: Editable?) = Unit
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                val searchText = s.toString().trim()
//                if(searchText == searchFor)
//                    return
//                searchFor = searchText
//                val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
//                coroutineScope.launch {
//                    delay(300)
//                    if (searchText != searchFor)
//                        return@launch
//
//                    viewModel.getEngineerList("")
//                }
//            }
//
//        })
//
//
//        return binding.root
//    }
//
//    @SuppressLint("FragmentLiveDataObserve")
//    private fun subscribeLiveData(){
//        viewModel.engineerListLiveData.observe(this, Observer {
//            (binding.recyclerviewSearch.adapter as EngineerAdapter).addList(it)
//        })
//
//        viewModel.isLoadingLiveData.observe(this, Observer {
//            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
//        })
//    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        (requireActivity() as AppCompatActivity).supportActionBar?.show()
//    }
//
//}