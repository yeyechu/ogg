package com.swu.ogg.ui.myactivity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.R
import com.swu.ogg.database.UserProject
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

        val doneFrame = view?.findViewById<FrameLayout>(R.id.done_layout)
        val textDone = view?.findViewById<TextView>(R.id.tv_done)

        fun bind(room : CardItem, context: Context, onClickListener : View.OnClickListener){
//            if(room.limit == 0){
//                //button?.text = "인증완료"
//                button?.visibility = View.INVISIBLE
//                doneFrame?.visibility = View.VISIBLE
//                textDone?.visibility = View.VISIBLE
//                textTitle?.visibility = View.INVISIBLE
//                textCo2?.visibility = View.INVISIBLE
//            }
            textTitle?.text = room.title
            textCo2?.text = room.co2
            image?.setImageBitmap(room.image)
            button?.setOnClickListener(onClickListener)
            doneFrame?.visibility
            textDone?.text
        }

        // 변수처럼 호출하기 위한 companion ocject 설정
        companion object {
            fun create(parent: ViewGroup) : CardViewHolder {

                val view : View = LayoutInflater.from(parent.context).inflate(R.layout.myactivity_card_item, parent, false)

                return CardViewHolder(view)
            }
        }
    }

    // 뷰 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {

        return  CardViewHolder.create(parent)
    }

    // 뷰 내용 전환
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        val cardItem = toList[position]

        holder.apply {

            // 등록 가능 횟수 확인해서 카드 비활성화
            var flag = 0

            if(flag == 1){
                doneFrame!!.visibility = View.VISIBLE
                doneFrame!!.bringToFront()
                button!!.isEnabled = false

                if(true){ // 리미트 날짜가 남아 있을 때
                    textDone!!.text = " " + "일 남음"
                }
            }

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