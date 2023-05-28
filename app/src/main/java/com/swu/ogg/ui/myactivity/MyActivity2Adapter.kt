package com.swu.ogg.ui.myactivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.R
import com.swu.ogg.ui.myactivity.post.PostActivity

class MyActivity2Adapter(val context : Context, val onList : ArrayList<CardItem>)
    : RecyclerView.Adapter<MyActivity2Adapter.CardViewHolder>() {

        class CardViewHolder(view : View?) : RecyclerView.ViewHolder(view!!) {

            var textTitle = view?.findViewById<TextView>(R.id.tv_title)
            var textCo2 = view?.findViewById<TextView>(R.id.tv_co2)
            //var image = view?.findViewById<ImageView>(R.id.)
            var button = view?.findViewById<Button>(R.id.btn)

            fun bind(room : CardItem, context: Context, onClickListener: OnClickListener) {

                textTitle?.text = room.title
                textCo2?.text = room.co2
                //image?.setImageBitmap(room.image)
                button?.setOnClickListener(onClickListener)
            }
        }

    // 뷰 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyActivity2Adapter.CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.myactivity_card_item2, parent, false)

        return MyActivity2Adapter.CardViewHolder(view)
    }

    // 뷰 내용 전환
    override fun onBindViewHolder(holder: MyActivity2Adapter.CardViewHolder, position: Int) {

        val cardItem = onList[position]

        holder.apply {
            bind(cardItem, context, View.OnClickListener {
                var intent : Intent = Intent(context, PostActivity::class.java)
                intent.putExtra("titleActivity", cardItem.title)
                context.startActivity(intent)
            })
        }
    }

    interface  OnItemClickListener {
        fun onClick(v: View, position: Int){
        }

    }

    private lateinit var itemClickListener : OnItemClickListener
    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun getItemCount() = onList.size
}