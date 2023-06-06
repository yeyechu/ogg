package com.swu.ogg.ui.myactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.R
import com.swu.ogg.database.UserProject
import com.swu.ogg.databinding.TestUserItemBinding

class TodayListAdaper : RecyclerView.Adapter<TodayListAdaper.CardViewHolder>() {

    private var projectList = emptyList<UserProject>()
    class CardViewHolder(val binding : TestUserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int) : CardViewHolder {
        val binding = TestUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }
    override fun onBindViewHolder(holder : CardViewHolder, position : Int) {

        val currentItem = projectList[position]

        holder.binding.text1.text = currentItem.project1.toString()
        holder.binding.text2.text = currentItem.project2.toString()
        holder.binding.text3.text = currentItem.project3.toString()
        holder.binding.text4.text = currentItem.project4.toString()
        holder.binding.text5.text = currentItem.project5.toString()
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    fun setDate(project : List<UserProject>) {
        projectList = project
        notifyDataSetChanged()
    }
}