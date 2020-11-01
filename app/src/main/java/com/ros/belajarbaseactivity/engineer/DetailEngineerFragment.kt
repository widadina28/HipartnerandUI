package com.ros.belajarbaseactivity.engineer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.Hire.HireActivity
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.FragmentDetailEngineerBinding
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import com.squareup.picasso.Picasso


class DetailEngineerFragment : Fragment() {
    private lateinit var binding: FragmentDetailEngineerBinding
    private lateinit var sharedpref: sharedprefutil
    private lateinit var viewModel: DetailEngineerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedpref = sharedprefutil(requireContext())
        binding = FragmentDetailEngineerBinding.inflate(inflater)
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        viewModel = ViewModelProvider(this).get(DetailEngineerViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setHireService(service)
        }
        viewModel.callApi()

        binding.btnHire.setOnClickListener {
            val intent = Intent(requireActivity(), HireActivity::class.java)
            startActivity(intent)
        }
        subscribeLiveData()
        return binding.root
    }

    fun subscribeLiveData() {
        viewModel.isResponseDetail.observe(viewLifecycleOwner, Observer {
            binding.tvNameEngineer.text = it.data?.nameEngineer
            binding.tvDescdetail.text = it.data?.descriptionEngineer
            binding.tvJobdetail.text = it.data?.nameFreelance
            binding.tvLocdetail.text = it.data?.nameLoc
            binding.tvRate.text = it.data?.rate
            binding.tvSkill.text = it.data?.nameSkill
            binding.tvStatusdetail.text = it.data?.status
            Picasso.get().load("http://3.80.45.131:8080/uploads/" + it.data?.image).placeholder(
                R.drawable.ic_baseline_person_24
            ).into(binding.imgengineer)
        })
    }


}