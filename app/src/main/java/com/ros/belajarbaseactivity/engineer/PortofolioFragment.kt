package com.ros.belajarbaseactivity.engineer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class PortofolioFragment: Fragment(){
    private lateinit var binding: FragmentPortofolioBinding
    private lateinit var rv: RecyclerView
    private lateinit var sharedpref: sharedprefutil
    private lateinit var portofolioAdapter : PortofolioAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortofolioBinding.inflate(inflater)
        sharedpref = sharedprefutil(requireContext())

        rv = binding.rvportofolio
        portofolioAdapter = PortofolioAdapter(
            arrayListOf(), object : PortofolioAdapter.OnAdapterListenerPorto {
                override fun onClick(portofolio: PortofolioModel) {
                    val a = sharedpref.putString(Constant.PREF_LINK_REPO, portofolio.linkRepo)
                    Log.d("Link Repo Fragment", "$a")
                    val intent = Intent(requireActivity(), LinkRepo::class.java)
                    startActivity(intent)
                }

            }
        )
        binding.rvportofolio.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = portofolioAdapter
        }
        callApi()
        return binding.root
    }

    private fun callApi (){
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        service?.getPortofolioByID(idEngineer)?.enqueue(object : retrofit2.Callback<PortofolioResponse>{
            override fun onFailure(call: Call<PortofolioResponse>, t: Throwable) {
                Log.e("Portofolio Fragment", t.message ?: "error")
            }

            override fun onResponse(
                call: Call<PortofolioResponse>,
                response: Response<PortofolioResponse>
            ) {
                Log.d("Portofolio", "${response.body()}")
                Log.d("idporto", "$idEngineer")
                val list = response.body()?.data?.map {
                    PortofolioModel(it.idPortofolio.orEmpty(), it.aplicationName.orEmpty(),
                        it.image.orEmpty(), it.linkRepo.orEmpty())} ?: listOf()
                (binding.rvportofolio.adapter as PortofolioAdapter).addList(list)
            }
        })

    }
}