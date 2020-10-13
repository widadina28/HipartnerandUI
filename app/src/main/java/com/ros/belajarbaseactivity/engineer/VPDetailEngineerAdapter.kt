package com.ros.belajarbaseactivity.engineer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class VPDetailEngineerAdapter(fragment: FragmentManager):
FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    val fragment = arrayOf(DetailEngineerFragment(), ExperienceFragment(), PortofolioFragment())

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getCount(): Int =fragment.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Profile"
            1 -> "Experience"
            2 -> "Portofolio"
            else -> ""
        }
    }
}