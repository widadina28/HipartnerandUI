package com.ros.belajarbaseactivity.bottomnav

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.ros.belajarbaseactivity.Onboard1
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.databinding.FragmentProfileBinding
import com.ros.belajarbaseactivity.home
import com.ros.belajarbaseactivity.sharedpref.sharedprefutil
import com.ros.belajarbaseactivity.webview.GithubWebVActivity


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

        val bt_ngithub =binding.btngithub
        bt_ngithub.setOnClickListener {
            startActivity(Intent(getActivity(), GithubWebVActivity::class.java))
        }

        sharedpref = sharedprefutil(requireContext())
        return binding.root
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
            R.id.edit_profile -> Toast.makeText(requireContext(), "THIS IS EDIT PROFILE", Toast.LENGTH_LONG).show()
            R.id.logout -> { dialog() }
        }
        return true
    }

    private fun dialog(){
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Are You Sure?")
            .setPositiveButton("Log Out") { dialog: DialogInterface?, which: Int ->
                sharedpref.clear()
                val intent=Intent(requireActivity(),home::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") {dialogInterface, i -> dialogInterface.dismiss()
            }
        dialog.show()

    }
}

