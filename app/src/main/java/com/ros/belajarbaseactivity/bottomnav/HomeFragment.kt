package com.ros.belajarbaseactivity.bottomnav

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var binding : FragmentHomeBinding
    private lateinit var rv : RecyclerView
    private lateinit var sharedpref: sharedprefutil
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        sharedpref = sharedprefutil(requireContext())

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
        callApi()

        return binding.root
    }

    private fun callApi () {
        binding.progressBar.visibility = View.VISIBLE
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
                (binding.recyclerview.adapter as EngineerAdapter).addList(list)
                }
            })
        binding.progressBar.visibility = View.GONE

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }


}