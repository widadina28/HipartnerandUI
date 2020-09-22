package com.ros.belajarbaseactivity.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_projects.view.*

class ProjectAdapter(private val projects:List<Project>):RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_projects, parent, false))
    }

    override fun getItemCount(): Int = projects.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bindProject(projects[position])
    }
}


class Holder(view: View):RecyclerView.ViewHolder(view) {
    private val  tvNameProject = view.tv_rv
    private val imgProject = view.imgrv
    fun bindProject(project: Project){
        tvNameProject.text=project.nameProject
        Picasso.get().load(project.image).into(imgProject)
    }
}