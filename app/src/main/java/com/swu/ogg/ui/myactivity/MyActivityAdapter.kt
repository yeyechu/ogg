package com.swu.ogg.ui.myactivity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.MainActivity
import com.swu.ogg.R
import com.swu.ogg.database.NewRecordActivity
import com.swu.ogg.ui.myactivity.post.PostActivity

// 오늘의 활동에 대한 어댑터

data class CardItem(
    val image : Bitmap,
    val title : String,
    val co2 : String,
)
class MyActivityAdapter (val context : Context, val toList : ArrayList<CardItem>)
    : RecyclerView.Adapter<MyActivityAdapter.CardViewHolder>() {

    class CardViewHolder(view : View?) : RecyclerView.ViewHolder(view!!) {

        val textTitle = view?.findViewById<TextView>(R.id.tv_title)
        val textCo2 = view?.findViewById<TextView>(R.id.tv_co2)
        val image = view?.findViewById<ImageView>(R.id.img_view)
        val button = view?.findViewById<Button>(R.id.btn)

        fun bind(room : CardItem, context: Context, onClickListener : View.OnClickListener){

            textTitle?.text = room.title
            textCo2?.text = room.co2
            image?.setImageBitmap(room.image)
            button?.setOnClickListener(onClickListener)

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

        val cardItem = toList[position]

        // 활동 카드 클릭 시 불러야 할 것 정의
        // Intent : 화면(Activity)
        // putExtra : 전달 해야할 내용

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

    override fun getItemCount() = toList.size
}