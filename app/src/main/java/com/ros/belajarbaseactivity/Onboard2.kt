package com.ros.belajarbaseactivity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ros.belajarbaseactivity.databinding.ActivityOnboard2Binding

class Onboard2 : Fragment() {

    private lateinit var binding: ActivityOnboard2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityOnboard2Binding.inflate(inflater)

        val tologin = binding.btnLogin1
        tologin.setOnClickListener {
            startActivity(Intent(requireActivity(), home::class.java))
        }
        return binding.root
    }
}
