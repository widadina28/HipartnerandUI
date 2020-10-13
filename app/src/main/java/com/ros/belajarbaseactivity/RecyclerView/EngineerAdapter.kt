package com.ros.belajarbaseactivity.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ros.belajarbaseactivity.R
import com.ros.belajarbaseactivity.engineer.EngineerModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_engineer.view.*

class EngineerAdapter(var dataEngineer:ArrayList<EngineerModel>, var listener: OnAdapterListenerEng): RecyclerView.Adapter<EngineerAdapter.HolderEng>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderEng {
        return HolderEng(
            LayoutInflater.from(parent.context).inflate(R.layout.item_engineer, parent, false)
        )
    }

    override fun getItemCount() = dataEngineer.size

    override fun onBindViewHolder(holder: HolderEng, position: Int) {
        val engineer = dataEngineer[position]
        Picasso.get().load("http://3.80.45.131:8080/uploads/" + engineer.image).placeholder(R.drawable.ic_baseline_person_24).into(holder.view.imageeng)
        holder.view.textViewname.text=engineer.nameEngineer
        holder.view.textViewjob.text=engineer.status
        holder.view.textViewloc.text=engineer.nameLoc
        holder.view.textViewrate.text=engineer.rate
        holder.view.textViewskill.text=engineer.nameSkill
        holder.view.imageeng.setOnClickListener {
            listener.onClick(engineer)
        }
    }

    class HolderEng(val view: View):RecyclerView.ViewHolder(view)

    fun addList(newList: List<EngineerModel>) {
        dataEngineer.clear()
        dataEngineer.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnAdapterListenerEng {
        fun onClick(engineer: EngineerModel)
    }
}

//class EngineerAdapter(var listener: onAdapterListenerEng): RecyclerView.Adapter<EngineerAdapter.EngineerHolder>() {
// private  val items = mutableListOf<EngineerModel>()
//
//    fun addList(list: List<EngineerModel>) {
//        items.clear()
//        items.addAll(list)
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): EngineerAdapter.EngineerHolder {
//        return EngineerHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_engineer, parent, false))
//    }
//
//    override fun getItemCount(): Int=items.size
//
//    override fun onBindViewHolder(holder: EngineerAdapter.EngineerHolder, position: Int) {
//        holder.bindItems(items[position])
//    }
//    class EngineerHolder (view : View) : RecyclerView.ViewHolder(view){
//        private val img = view.imageeng
//        private val name = view.textViewname
//        private val job = view.textViewjob
//        private val loc = view.textViewloc
//        private val rate = view.textViewrate
//        private val skill = view.textViewskill
//        fun bindItems(emodel : EngineerModel){
//            Picasso.get().load(emodel.image).into(img)
//            name.text=emodel.nameEngineer
//            job.text=emodel.status
//            loc.text=emodel.nameLoc
//            rate.text=emodel.rate
//            skill.text=emodel.nameSkill
//        }
//    }
//
//    interface onAdapterListenerEng{
//        fun onClick()
//
//    }
//}