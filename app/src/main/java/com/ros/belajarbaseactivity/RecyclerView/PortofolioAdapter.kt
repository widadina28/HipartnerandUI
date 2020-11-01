package com.ros.belajarbaseactivity.RecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.engineer.PortofolioModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_portofolio.view.*

class PortofolioAdapter(
    val dataportofolio: ArrayList<PortofolioModel>,
    var listener: OnAdapterListenerPorto
) : RecyclerView.Adapter<PortofolioAdapter.HolderPortofolio>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PortofolioAdapter.HolderPortofolio {
        return HolderPortofolio(
            LayoutInflater.from(parent.context).inflate(R.layout.item_portofolio, parent, false)
        )
    }

    override fun getItemCount(): Int = dataportofolio.size

    override fun onBindViewHolder(holder: PortofolioAdapter.HolderPortofolio, position: Int) {
        val portofolio = dataportofolio[position]
        holder.view.link_repo_portofolio.text = portofolio.linkRepo
        holder.view.name_portofolio.text = portofolio.appName
        Picasso.get().load("http://3.80.45.131:8080/uploads/" + portofolio.image)
            .placeholder(R.drawable.ic_picture).into(holder.view.iv_portofolio)
        holder.view.link_repo_portofolio.setOnClickListener {
            listener.onClick(portofolio)
        }

    }

    class HolderPortofolio(val view: View) : RecyclerView.ViewHolder(view)

    interface OnAdapterListenerPorto {
        fun onClick(portofolio: PortofolioModel)
    }

    fun addList(newList: List<PortofolioModel>) {
        dataportofolio.clear()
        dataportofolio.addAll(newList)
        notifyDataSetChanged()

    }

}