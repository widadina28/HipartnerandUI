package com.ros.belajarbaseactivity.bottomnav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.RecyclerView.Project
import com.ros.belajarbaseactivity.RecyclerView.ProjectAdapter
import com.ros.belajarbaseactivity.databinding.FragmentProjectBinding

class ProjectFragment : Fragment() {
    private lateinit var binding :FragmentProjectBinding
    private lateinit var rv:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectBinding.inflate(inflater)
        // Inflate the layout for this fragment

        rv = binding.recyclerview

        val listProjects = listOf(
            Project("Mobile Aplication Developer", image = "https://is2-ssl.mzstatic.com/image/thumb/Purple124/v4/c4/eb/a0/c4eba0a5-cd47-12a5-7e48-86b4c2dbd5dd/AppIcon-0-0-1x_U007emarketing-0-0-0-5-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.jpeg/1200x630wa.png"),
            Project("Mobile Game Developer", image ="https://rec-data.kalibrr.com/www.kalibrr.com/logos/GJP6S36Z6UENQ9R2NE4HQNPMYL2Z7HH5V75XZ6E7-5dc506ee.png" ),
            Project("Mobile App Developer", image ="https://3.bp.blogspot.com/-qHgsSTGvEqg/XDw0UP8o_zI/AAAAAAAAGKk/U5oXUujQp94HjQSsDcjim6V5szRUz4BlQCK4BGAYYCw/s400/logo-tokopedia.png" ),
            Project("Mobile Aplication Developer", image = "https://cdn.techinasia.com/wp-content/uploads/2019/07/Untitled.png"),
            Project("Mobile Game Developer", image ="https://asset-a.grid.id/crop/266x0:2071x1030/360x240/photo/2019/07/02/3102388760.png" ),
            Project("Analyst Data", image ="https://www.static-src.com/siva/asset//05_2020/blibli-home-og-2020.png" ),
            Project("Build Marketplace Platform", image = "https://2.bp.blogspot.com/-jGWB0rjFIjM/WxN7yDH-eCI/AAAAAAAAPwg/2qT3dCRHVhc77QLE6jWi3kMziV9DFSZygCLcBGAs/w1200-h630-p-k-no-nu/facebook%2Bmarketplace%2Bindonesia.png"),
            Project("Wordpress PHP Developer", image ="https://miro.medium.com/max/3840/1*a2zuB3sbks0JI1A19W7hYQ.jpeg" ),
            Project("Android Developer", image ="https://sdtimes.com/wp-content/uploads/2018/03/2NqZJYQI_400x400.png" ),
            Project("React Native Developer", image = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/React-icon.svg/1200px-React-icon.svg.png"),
            Project("Web Developer", image ="https://www.kalibrr.id/assets/shared/img/logos/kalibrr-blue-mobile.__69a1cacc_brown_.png" ),
            Project("Data Scientist", image ="https://cdn2.tstatic.net/jogja/foto/bank/images/bank-bca.jpg" ),
            Project("Data Admin", image = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRTe1NialKLpsp9r05wXRVRBI2nryNs0kk72g&usqp=CAU"),
            Project("Database My SQL", image ="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRz2x7JIMbsgAqqK_AbLWhxMekvhTCEQY1mmg&usqp=CAU" ),
            Project("Mobile App Developer", image ="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTHfmiiYZ2rPO9Us-4E7NTNKE5ROb0WyF_dpA&usqp=CAU" ),
            Project("Mobile Aplication Developer", image = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRLlbKim9iFyMP1lb6BPeubmykZ9twroayxjQ&usqp=CAU"),
            Project("Mobile Developer", image ="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQtIrZZFdMiSm9a1Ju1suerKG6KLkW_ut2Mfw&usqp=CAU" ),
            Project("Mobile App Developer", image ="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRuSk7AQfbiRze_cmZnUjioBDVFXi0Lay5W2g&usqp=CAU" )
        )

        val projectsAdapter = ProjectAdapter(listProjects)

        binding.recyclerview.apply {
            rv.layoutManager = LinearLayoutManager(requireContext())
            rv.adapter = projectsAdapter
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }
}