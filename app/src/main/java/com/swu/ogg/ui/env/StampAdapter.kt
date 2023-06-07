package com.swu.ogg.ui.env

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.swu.ogg.R

// 스탬프에 대한 데이터 클래스
data class StampItem(
    val day : Int,
    val percent : Float,
    val today : Int
)

class StampAdapter(val context : Context, val stamplist : ArrayList<StampItem>) : BaseAdapter() {

    override fun getCount(): Int = stamplist.size

    override fun getItem(position : Int) : StampItem = stamplist[position]

    override fun getItemId(position : Int): Long {
        return 0
    }

    override fun getView(position : Int, view : View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(parent?.context).inflate(R.layout.stamp_item, null)

        var textDay : TextView = view!!.findViewById(R.id.tv_stamp_day)
        val stamps = stamplist[position]

        textDay.text = stamps.day.toString()

        when(stamps.percent) {

            // 하나도 못했을 때 스탬프
            0f -> textDay.setBackgroundResource(R.drawable.calendersticker_1)
            // 50%일 때 스탬프
            0.5f -> textDay.setBackgroundResource(R.drawable.calendersticker_2)
            // 100% 이상일 때 스탬프
            1f -> textDay.setBackgroundResource(R.drawable.calendersticker_3)
            else -> textDay.setBackgroundResource(R.drawable.calendersticker_3)
        }

        if(stamps.day > stamps.today) {

            textDay.setBackgroundResource(R.drawable.stamp_drawable)

        } else if(stamps.day < stamps.today) {

            textDay.text = ""

        } else {

            // 오늘 날짜 설정
            textDay.setTextColor(Color.parseColor("#6897F3"))
            textDay.setBackgroundResource(R.drawable.stamp_today_drawable)
        }

        return view
    }

}