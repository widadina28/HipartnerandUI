package com.ros.belajarbaseactivity.onboardvp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ros.belajarbaseactivity.Onboard1
import com.ros.belajarbaseactivity.Onboard2

class ViewPagerAdapter(fragment: FragmentManager) :
    FragmentStatePagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val fragment = arrayOf(Onboard1(), Onboard2())

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getCount(): Int =fragment.size
}