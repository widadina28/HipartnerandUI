package com.ros.belajarbaseactivity.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.engineer.ExperienceModel
import kotlinx.android.synthetic.main.item_experience.view.*

class ExperienceAdapter(val dataExperience:ArrayList<ExperienceModel>):RecyclerView.Adapter<ExperienceAdapter.HolderExp>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceAdapter.HolderExp {
        return HolderExp(LayoutInflater.from(parent.context).inflate(R.layout.item_experience, parent, false))
    }

    override fun getItemCount(): Int = dataExperience.size

    override fun onBindViewHolder(holder: ExperienceAdapter.HolderExp, position: Int) {
        val experience = dataExperience[position]
        holder.view.name_company_experience.text=experience.companyName
        holder.view.description_experience.text=experience.description
        holder.view.position_experience.text=experience.position
        holder.view.end_experience.text=experience.end
        holder.view.start_experience.text=experience.start
    }

    class HolderExp(val view: View): RecyclerView.ViewHolder(view)

    fun addList(newList: List<ExperienceModel>){
        dataExperience.clear()
        dataExperience.addAll(newList)
        notifyDataSetChanged()
    }
}