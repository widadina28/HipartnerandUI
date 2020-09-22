package com.ros.belajarbaseactivity

import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.databinding.ActivityProfilScreenBinding

class ProfilScreen : BaseActivity() {
    private lateinit var binding:ActivityProfilScreenBinding

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profil_screen)
    }

    override fun onCreateActivity() {
        supportActionBar?.hide()
        val intent = intent
        val name = intent.getStringExtra("NAMEform")
        val dob = intent.getStringExtra("DOBform")
        val id = intent.getStringExtra("IDform")
        val gpa = intent.getStringExtra("GPAform")
        val edu = intent.getStringExtra("EDUform")
        val achiv = intent.getStringExtra("ACHIVform")
        val exp = intent.getStringExtra("EXPform")

        binding.tvText.text = "NAME:\n"+name+""
        binding.tvText1.text = "DATE OF BIRTH:\n"+dob+""
        binding.tvText2.text = "IDENTITY NUMBER:\n"+id+""
        binding.tvText3.text = "GPA:\n"+gpa+""
        binding.tvText4.text = "EDUCATION:\n"+edu+""
        binding.tvText5.text = "ACHIVEMENT:\n"+achiv+""
        binding.tvText.text = "EXPERIENCE:\n"+exp+""
    }

    override fun initListener() {}

}