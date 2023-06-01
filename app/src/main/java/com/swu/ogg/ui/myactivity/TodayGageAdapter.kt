package com.swu.ogg.ui.myactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.R
import com.swu.ogg.ui.env.GageItem

// 오늘 게이지 구현할 어댑터 정의
class TodayGageAdapter(val context : Context, val gageToList : ArrayList<GageItem>)
    : RecyclerView.Adapter<TodayGageAdapter.GageViewHolder>() {

    class GageViewHolder(view : View?) : RecyclerView.ViewHolder(view!!) {

        val textCo2Alarm = view?.findViewById<TextView>(R.id.tv_co2_alarm)
        val textCo2Aim = view?.findViewById<TextView>(R.id.tv_co2_aim_gage)
        val layoutAlarm = view?.findViewById<LinearLayout>(R.id.alarm_box_layout)
        val progressBar = view?.findViewById<ProgressBar>(R.id.determinateBar)
        fun bind(room : GageItem, context: Context) {

            textCo2Alarm?.text = room.co2Left.toString()
            textCo2Aim?.text = room.aim.toString()
            layoutAlarm
            progressBar!!.progress
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gage_item, parent, false)

        return GageViewHolder(view)
    }

    override fun onBindViewHolder(holder: GageViewHolder, position: Int) {

        val gageItem = gageToList[position]

        holder.apply {

        }
    }

    override fun getItemCount(): Int = gageToList.size
}