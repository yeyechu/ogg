package com.swu.ogg.ui.myactivity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.R

data class CardItem(
    val image : Bitmap,
    val title : String,
    val co2 : String,
)
class MyActivityAdapter (val context : Context, val dataSet : ArrayList<CardItem>)
    : RecyclerView.Adapter<MyActivityAdapter.CardViewHolder>() {

    class CardViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        var textTitle = view?.findViewById<TextView>(R.id.tv_title)
        var textCo2 = view?.findViewById<TextView>(R.id.tv_co2)
        var image = view?.findViewById<ImageView>(R.id.img_view)
        var layout = view?.findViewById<LinearLayout>(R.id.cardLayout)

        fun bind(room : CardItem, context: Context, onClickListener : View.OnClickListener){

            textTitle?.text = room.title
            textCo2?.text = room.co2
            image?.setImageBitmap(room.image)
            layout?.setOnClickListener(onClickListener)

        }
    }

    // 뷰 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.myactivity_card_item, parent, false)

        return  CardViewHolder(view)
    }

    // 뷰 내용 전환
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        val cardItem = dataSet[position]

        holder.apply {
            bind(cardItem, context, View.OnClickListener {

            })
        }
    }

    override fun getItemCount() = dataSet.size
}