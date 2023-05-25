package com.swu.ogg.ui.myactivity.post

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.R


// 리사이클러뷰를 위한 어댑터 및 뷰 홀더



class PostAdapter (val context : Context, val dataSet : ArrayList<String>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var layout = view?.findViewById<ConstraintLayout>(R.id.postLayout)
        var textTitle = view?.findViewById<TextView>(R.id.tv_title)
        var textCo2 = view?.findViewById<TextView>(R.id.tv_co2)
        var textFreq = view?.findViewById<TextView>(R.id.tv_content_freq)
        var textGuide = view?.findViewById<TextView>(R.id.tv_content_guide)

    }

    // 뷰 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)

        return  PostViewHolder(view)
    }

    // 뷰 내용 전환
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

    }

    override fun getItemCount() = dataSet.size
}