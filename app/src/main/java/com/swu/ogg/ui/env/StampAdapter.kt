package com.swu.ogg.ui.env

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.swu.ogg.R
import com.swu.ogg.database.DateSet

// 스탬프에 대한 데이터 클래스
data class StampItem(
    val day : Int,
    val Co2Today : String,
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
        //var stampToday : ImageView = view!!.findViewById(R.id.image_today_sticker)
        val stamps = stamplist[position]

        textDay.text = stamps.day.toString()

        if(stamps.today == stamps.day)
        {
            // 오늘 날짜 설정
            textDay.setTextColor(Color.parseColor("#6897F3"))
            textDay.setBackgroundResource(R.drawable.stamp_today_drawable)

        } else if(stamps.day > stamps.today) {

            textDay.setBackgroundResource(R.drawable.stamp_drawable)

        } else if(stamps.day < stamps.today) {

            textDay.text = ""

            when(stamps.Co2Today.toFloat()) {

                // 하나도 못했을 때 스탬프
                0f -> textDay.setBackgroundResource(R.drawable.calendersticker_1)
                // 50%일 때 스탬프
                in 0.001f..0.7f -> textDay.setBackgroundResource(R.drawable.calendersticker_2)
                // 100% 이상일 때 스탬프
                else -> textDay.setBackgroundResource(R.drawable.calendersticker_3)

//            0f -> stampToday.setImageResource(R.drawable.calendersticker_1)
//            0.7f -> stampToday.setImageResource(R.drawable.calendersticker_2)
//            else -> stampToday.setImageResource(R.drawable.calendersticker_3)
            }
        }

        return view
    }

}