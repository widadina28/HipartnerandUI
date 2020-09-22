package com.ros.belajarbaseactivity

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.ros.belajarbaseactivity.databinding.ActivityFormBinding


class form : BaseActivity() {
    private lateinit var binding: ActivityFormBinding

    override fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_form)
    }

    override fun onCreateActivity() {
        supportActionBar?.hide()
    }

    override fun initListener() {

        binding.btnSubmit.setOnClickListener {
            val nameEt = binding.etNameform.text.toString()
            val dobEt = binding.etDob.text.toString()
            val idEt = binding.etId.text.toString()
            val gpaEt = binding.etGpa.text.toString()
            val eduEt = binding.etEd.text.toString()
            val achivEt = binding.etAchivement.text.toString()
            val expEt = binding.etExp.text.toString()

            val intent = Intent (this, ProfilScreen::class.java)
            intent.putExtra("NAMEform","$nameEt")
            intent.putExtra("DOBform","$dobEt")
            intent.putExtra("IDform","$idEt")
            intent.putExtra("GPAform","$gpaEt")
            intent.putExtra("EDUform","$eduEt")
            intent.putExtra("ACHIVform","$achivEt")
            intent.putExtra("EXPform","$expEt")
            startActivity(intent)
        }
    }
}