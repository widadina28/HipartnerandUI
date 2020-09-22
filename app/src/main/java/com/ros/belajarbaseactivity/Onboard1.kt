package com.ros.belajarbaseactivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ros.belajarbaseactivity.databinding.ActivityOnboard1Binding

class Onboard1 : Fragment() {
    private lateinit var binding: ActivityOnboard1Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityOnboard1Binding.inflate(inflater)
        return binding.root
    }
}