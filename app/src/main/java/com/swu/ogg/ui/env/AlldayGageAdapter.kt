package com.swu.ogg.ui.env

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.R
import com.swu.ogg.ui.myactivity.post.PostActivity

// 전체 게이지 구현할 어댑터 정의
data class GageItem(
    val co2 : String,
    val aim : String,
    val percent : Float
)

data class SummaryItem(
    val day : Int,
    val co2Left : String,
    val image : Bitmap
)

class AlldayGageAdapter(val context : Context, val gageAllList : ArrayList<GageItem>)
    : RecyclerView.Adapter<AlldayGageAdapter.GageViewHolder>() {

    class GageViewHolder(view : View?) : RecyclerView.ViewHolder(view!!) {

        val textCo2Alarm = view?.findViewById<TextView>(R.id.tv_co2_alarm)
        val textCo2Aim = view?.findViewById<TextView>(R.id.tv_co2_aim)
        val layoutAlarm = view?.findViewById<LinearLayout>(R.id.alarm_box_layout)
        val progressBar = view?.findViewById<ProgressBar>(R.id.determinateBar)

        fun bind(room : GageItem) {

            textCo2Alarm?.text = room.co2
            textCo2Aim?.text = room.aim
            layoutAlarm!!.visibility = View.GONE
            progressBar!!.progress
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gage_item, parent, false)

        return GageViewHolder(view)
    }

    override fun onBindViewHolder(holder: GageViewHolder, position: Int) {

        val gageItem = gageAllList[position]

        holder.apply {

        }
    }

    override fun getItemCount(): Int = gageAllList.size
}