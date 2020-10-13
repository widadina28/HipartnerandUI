package com.ros.belajarbaseactivity.engineer

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
import com.ros.belajarbaseactivity.RecyclerView.ExperienceAdapter
import com.ros.belajarbaseactivity.databinding.FragmentExperienceBinding
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ExperienceFragment : Fragment(){
    private lateinit var binding: FragmentExperienceBinding
    private lateinit var rv : RecyclerView
    private lateinit var sharedpref: sharedprefutil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExperienceBinding.inflate(inflater)
        sharedpref = sharedprefutil(requireContext())
        rv = binding.rvexperience
        rv.adapter = ExperienceAdapter(arrayListOf())
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        callApi()

        return binding.root
    }

    private fun callApi () {
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        service?.getExperienceByID(idEngineer)?.enqueue(object : retrofit2.Callback<ExperienceResponse>{
            override fun onFailure(call: Call<ExperienceResponse>, t: Throwable) {
                Log.e("Experience Fragment", t.message ?: "error")
            }

            override fun onResponse(
                call: Call<ExperienceResponse>,
                response: Response<ExperienceResponse>
            ) {
                Log.d("ExperienceFragment", "${response.body()}")
                Log.d("ideng", "$idEngineer")
                val list = response.body()?.data?.map {
                    ExperienceModel(it.idExperience.orEmpty(), it.position.orEmpty(), it.companyName.orEmpty(),
                    it.description.orEmpty(), it.start.orEmpty(), it.end.orEmpty()
                    )}?: listOf()
                Log.d("List", "$list")
                (binding.rvexperience.adapter as ExperienceAdapter).addList(list)
            }

        })
    }

}