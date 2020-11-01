package com.ros.belajarbaseactivity.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.Project.ProjectAllModel
import com.ros.belajarbaseactivity.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_projects.view.*

class ProjectAdapter(var dcProject: ArrayList<ProjectAllModel>, var listener: OnAdapterListener) :
    RecyclerView.Adapter<ProjectAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_projects, parent, false)
        )
    }

    override fun getItemCount() = dcProject.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val project = dcProject[position]
        holder.view.tv_name_project.text = project.projectName
        holder.view.tv_expired.text = project.deadline
        Picasso.get().load("http://3.80.45.131:8080/uploads/" + project.image)
            .placeholder(R.drawable.ic_image_editing).into(holder.view.image_project)
        holder.view.image_project.setOnClickListener {
            listener.onClick(project)
        }
        holder.view.edit_project.setOnClickListener {
            listener.onUpdate(project)
        }
        holder.view.delete_project.setOnClickListener {
            listener.onDelete(project)
        }

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(newList: List<ProjectAllModel>) {
        dcProject.clear()
        dcProject.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(project: ProjectAllModel)
        fun onUpdate(project: ProjectAllModel)
        fun onDelete(project: ProjectAllModel)
    }


}

