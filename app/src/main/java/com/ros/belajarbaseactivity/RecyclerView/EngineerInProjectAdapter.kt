package com.ros.belajarbaseactivity.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.Hire.EngineerInProjectModel
import com.ros.belajarbaseactivity.R
import kotlinx.android.synthetic.main.item_engineer_in_project.view.*

class EngineerInProjectAdapter(val dataEngineerInProject: ArrayList<EngineerInProjectModel>) :
    RecyclerView.Adapter<EngineerInProjectAdapter.HolderEng>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EngineerInProjectAdapter.HolderEng {
        return HolderEng(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_engineer_in_project, parent, false)
        )
    }

    override fun getItemCount(): Int = dataEngineerInProject.size

    class HolderEng(val view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: EngineerInProjectAdapter.HolderEng, position: Int) {
        val engineer = dataEngineerInProject[position]
        holder.view.name_engineer_in_project.text = engineer.nameEngineer
        holder.view.status_in_project.text = engineer.status
    }

    fun addList(newList: List<EngineerInProjectModel>) {
        dataEngineerInProject.clear()
        dataEngineerInProject.addAll(newList)
        notifyDataSetChanged()
    }

}