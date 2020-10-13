package com.ros.belajarbaseactivity.engineer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.Hire.HireActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.FragmentDetailEngineerBinding
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_engineer.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DetailEngineerFragment : Fragment() {
    private lateinit var binding: FragmentDetailEngineerBinding
    private lateinit var sharedpref: sharedprefutil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedpref = sharedprefutil(requireContext())
        binding = FragmentDetailEngineerBinding.inflate(inflater)
        callApi()

        binding.btnHire.setOnClickListener {
            val intent = Intent(requireActivity(), HireActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun callApi (){
        val idEngineer = sharedpref.getString(Constant.PREF_ID_ENGINEER)
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)

        service?.getEngineerByID(idEngineer)?.enqueue(object : retrofit2.Callback<EngineerResponseID>{
            override fun onFailure(call: Call<EngineerResponseID>, t: Throwable) {
                Log.e("DetailEngineerFragment", t.message ?: "error")
            }

            override fun onResponse(
                call: Call<EngineerResponseID>,
                response: Response<EngineerResponseID>
            ) {
                Log.d("Detail Engineer", "${response.body()}")
                Log.d("id_Detail", "$idEngineer")
                binding.tvNameEngineer.text = response.body()?.data?.nameEngineer
                binding.tvDescdetail.text = response.body()?.data?.descriptionEngineer
                binding.tvJobdetail.text = response.body()?.data?.nameFreelance
                binding.tvLocdetail.text = response.body()?.data?.nameLoc
                binding.tvRate.text = response.body()?.data?.rate
                binding.tvSkill.text = response.body()?.data?.nameSkill
                binding.tvStatusdetail.text = response.body()?.data?.status
                Picasso.get().load("http://3.80.45.131:8080/uploads/" + response.body()?.data?.image).placeholder(R.drawable.ic_baseline_person_24).into(binding.imgengineer)
            }

        })
}
}