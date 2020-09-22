package com.ros.belajarbaseactivity.bottomnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ros.belajarbaseactivity.databinding.FragmentHomeBinding
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var sharepref : sharedprefutil


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        // Inflate the layout for this fragment

        sharepref = sharedprefutil(requireContext())

        var textHello = binding.nameHello
        textHello.text = sharepref.getString(Constant.PREF_EMAIL)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

}