package com.ros.belajarbaseactivity.bottomnav

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.ros.belajarbaseactivity.API.ApiClient
import com.ros.belajarbaseactivity.API.AuthApiService
import com.ros.belajarbaseactivity.Onboard1
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.FragmentProfileBinding
import com.ros.belajarbaseactivity.login.Login
import com.ros.belajarbaseactivity.profilecompany.CompanyByIDResponse
import com.ros.belajarbaseactivity.profilecompany.CompanyResponse
import com.ros.belajarbaseactivity.profilecompany.EditCompany
import com.ros.belajarbaseactivity.sharedpref.Constant
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import com.ros.belajarbaseactivity.webview.GithubWebVActivity
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var mdrawerlayout: DrawerLayout
    private lateinit var sharedpref: sharedprefutil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentProfileBinding.inflate(inflater)
        val navigationView : NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)

        mdrawerlayout = binding.drawerlayout
        val mtoolbar = binding.toolbarnew
        val activity: AppCompatActivity = activity as AppCompatActivity
        activity.setSupportActionBar(mtoolbar)
        activity.supportActionBar?.setHomeButtonEnabled(false)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        sharedpref = sharedprefutil(requireContext())
        callApi()
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        callApi()
    }
    private fun callApi(){
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        val idAcc = sharedpref.getString(Constant.PREF_ID_ACC)
        service?.getCompanyID(idAcc)?.enqueue(object :Callback<CompanyByIDResponse>{
            override fun onFailure(call: Call<CompanyByIDResponse>, t: Throwable) {
                Log.d("Profile Fragment", "error: $t")
            }

            override fun onResponse(
                call: Call<CompanyByIDResponse>,
                response: Response<CompanyByIDResponse>
            ) {
                Log.d("Detail company", "${response.body()}")
                binding.tvNameCompany.text=response.body()?.data?.nameCompany
                binding.tvFieldCompany.text=response.body()?.data?.field
                binding.tvLinkedin.text=response.body()?.data?.linkedinCompany
                binding.tvInstagramCompany.text=response.body()?.data?.instagramCompany
                binding.tvEmailCompany.text=response.body()?.data?.emailAccount
                binding.descriptionCompany.text=response.body()?.data?.descriptionCompany
                binding.tvLocationCompany.text=response.body()?.data?.nameLoc
                Picasso.get().load("http://3.80.45.131:8080/uploads/" + response.body()?.data?.image).placeholder(R.drawable.ic_baseline_person_24)
                    .into(binding.profileCompany)
                sharedpref.putString(Constant.PREF_ID_COMPANY, response.body()?.data?.idCompany)
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuToUse: Int = R.menu.right_side_icon_menu
        inflater.inflate(menuToUse, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item != null && item.getItemId() == R.id.menu_right) {
            if (mdrawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                mdrawerlayout.closeDrawer(Gravity.RIGHT);
            } else {
                mdrawerlayout.openDrawer(Gravity.RIGHT);
            }
            return true;
        }
        return false;
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.faq -> Toast.makeText(requireContext(), "THIS IS FAQ", Toast.LENGTH_SHORT).show()
            R.id.help -> Toast.makeText(requireContext(), "THIS IS HELP", Toast.LENGTH_LONG).show()
            R.id.aboutus -> Toast.makeText(requireContext(), "THIS IS ABOUT US", Toast.LENGTH_LONG).show()
            R.id.edit_profile -> {startActivity(Intent(requireContext(), EditCompany::class.java))}
            R.id.logout -> { dialog() }
        }
        return true
    }

    private fun dialog(){
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Are You Sure?")
            .setPositiveButton("Log Out") { dialog: DialogInterface?, which: Int ->
                sharedpref.clear()
                val intent=Intent(requireActivity(), Login::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") {dialogInterface, i -> dialogInterface.dismiss()
            }
        dialog.show()

    }
}


//class ProfileFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener {
//    private lateinit var binding: FragmentProfileBinding
//    private lateinit var mdrawerlayout: DrawerLayout
//    private lateinit var sharedpref: sharedprefutil
//    val idAcc = sharedpref.getString(Constant.PREF_ID_ACC)
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        setHasOptionsMenu(true)
//        binding = FragmentProfileBinding.inflate(inflater)
//        val navigationView : NavigationView = binding.navView
//        navigationView.setNavigationItemSelectedListener(this)
//
//        mdrawerlayout = binding.drawerlayout
//        val mtoolbar = binding.toolbarnew
//        val activity: AppCompatActivity = activity as AppCompatActivity
//        activity.setSupportActionBar(mtoolbar)
//        activity.supportActionBar?.setHomeButtonEnabled(false)
//        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
//
////        val bt_ngithub =binding.btngithub
////        bt_ngithub.setOnClickListener {
////            startActivity(Intent(getActivity(), GithubWebVActivity::class.java))
////        }
//
//        sharedpref = sharedprefutil(requireContext())
//        return binding.root
//    }
//
//
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        val menuToUse: Int = R.menu.right_side_icon_menu
//        inflater.inflate(menuToUse, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item != null && item.getItemId() == R.id.menu_right) {
//            if (mdrawerlayout.isDrawerOpen(Gravity.RIGHT)) {
//                mdrawerlayout.closeDrawer(Gravity.RIGHT);
//            } else {
//                mdrawerlayout.openDrawer(Gravity.RIGHT);
//            }
//            return true;
//        }
//        return false;
//    }
//
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.faq -> Toast.makeText(requireContext(), "THIS IS FAQ", Toast.LENGTH_SHORT).show()
//            R.id.help -> Toast.makeText(requireContext(), "THIS IS HELP", Toast.LENGTH_LONG).show()
//            R.id.aboutus -> Toast.makeText(requireContext(), "THIS IS ABOUT US", Toast.LENGTH_LONG).show()
//            R.id.edit_profile -> Toast.makeText(requireContext(), "THIS IS EDIT PROFILE", Toast.LENGTH_LONG).show()
//            R.id.logout -> { dialog() }
//        }
//        return true
//    }
//
//    private fun dialog(){
//        val dialog = AlertDialog.Builder(requireContext())
//            .setTitle("Are You Sure?")
//            .setPositiveButton("Log Out") { dialog: DialogInterface?, which: Int ->
//                sharedpref.clear()
//                val intent=Intent(requireActivity(),Login::class.java)
//                startActivity(intent)
//            }
//            .setNegativeButton("Cancel") {dialogInterface, i -> dialogInterface.dismiss()
//            }
//        dialog.show()
//
//    }
////
////    private fun callApi() {
////        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
////        val TAG = ProfileFragment::class.java
////        service?.getCompanyID()?.enqueue(object : retrofit2.Callback<CompanyResponse>{
////            override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
////                Log.e("android1", t.message ?: "error")
////            }
////
////            override fun onResponse(
////                call: Call<CompanyResponse>,
////                response: Response<CompanyResponse>
////            ) {
////                val responses = response.body()
////                val listid = responses?.data?.map {
////                    it.idAccount
////                }?: listOf()
////                val list = responses.data.map {
////
////                }
////                val lengthData = listid.size
////                for (i in 0..lengthData) {
////                    if (listid[i] == idAcc) {
////                        doInsert()
////                    }
////                }
////            }
////
////        })
////    }
////    private fun doInsert() {
////    }
//}

