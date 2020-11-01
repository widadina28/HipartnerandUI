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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
    private lateinit var viewModel: ProfileFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentProfileBinding.inflate(inflater)
        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)
        sharedpref = sharedprefutil(requireContext())
        val service = ApiClient.getApiClient(requireContext())?.create(AuthApiService::class.java)
        viewModel = ViewModelProvider(this).get(ProfileFragmentViewModel::class.java)
        viewModel.setSharedPreference(sharedpref)
        if (service != null) {
            viewModel.setHireService(service)
        }



        mdrawerlayout = binding.drawerlayout
        val mtoolbar = binding.toolbarnew
        val activity: AppCompatActivity = activity as AppCompatActivity
        activity.setSupportActionBar(mtoolbar)
        activity.supportActionBar?.setHomeButtonEnabled(false)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        sharedpref = sharedprefutil(requireContext())
        viewModel.callApi()
        subscribeLiveData()
        return binding.root
    }

    private fun subscribeLiveData() {
        viewModel.isResponseProfile.observe(viewLifecycleOwner, Observer {
            binding.tvNameCompany.text = it.data?.nameCompany
            binding.tvFieldCompany.text = it.data?.field
            binding.tvLinkedin.text = it.data?.linkedinCompany
            binding.tvInstagramCompany.text = it.data?.instagramCompany
            binding.tvEmailCompany.text = it.data?.emailAccount
            binding.descriptionCompany.text = it.data?.descriptionCompany
            binding.tvLocationCompany.text = it.data?.nameLoc
            Picasso.get().load("http://3.80.45.131:8080/uploads/" + it.data?.image).placeholder(
                R.drawable.ic_baseline_person_24
            )
                .into(binding.profileCompany)
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.callApi()
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
            R.id.aboutus -> Toast.makeText(requireContext(), "THIS IS ABOUT US", Toast.LENGTH_LONG)
                .show()
            R.id.edit_profile -> {
                startActivity(Intent(requireContext(), EditCompany::class.java))
            }
            R.id.logout -> {
                dialog()
            }
        }
        return true
    }

    private fun dialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Are You Sure?")
            .setPositiveButton("Log Out") { dialog: DialogInterface?, which: Int ->
                sharedpref.clear()
                val intent = Intent(requireActivity(), Login::class.java)
                startActivity(intent)
                activity?.supportFragmentManager?.popBackStack()
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
        dialog.show()

    }
}

