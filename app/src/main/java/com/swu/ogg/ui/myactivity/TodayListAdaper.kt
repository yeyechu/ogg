package com.swu.ogg.ui.myactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.R
import com.swu.ogg.database.UserProject

class TodayListAdaper : ListAdapter<UserProject, TodayListAdaper.CardViewHolder>(CardComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType : Int) : CardViewHolder {
        return CardViewHolder.create(parent)
    }
    override fun onBindViewHolder(holder : CardViewHolder, position : Int) {

        val current = getItem(position)
        holder.bind(current.uIndex)
    }
    class CardViewHolder(view : View?) : RecyclerView.ViewHolder(view!!) {

        val textTitle = view?.findViewById<TextView>(R.id.tv_title)
        val textCo2 = view?.findViewById<TextView>(R.id.tv_co2)
        val image = view?.findViewById<ImageView>(R.id.img_view)
        val button = view?.findViewById<Button>(R.id.btn)

        val doneFrame = view?.findViewById<FrameLayout>(R.id.done_layout)
        val textDone = view?.findViewById<TextView>(R.id.tv_done)

        fun bind(Index : Int?) {

            textTitle?.text = Index.toString()
//            textCo2?.text
//            image?.setImageBitmap(room.image)
//            button?.setOnClickListener(onClickListener)
//            doneFrame?.visibility
//            textDone?.text
        }

        companion object {
            fun create(parent : ViewGroup) : CardViewHolder {

                val view : View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.myactivity_card_item, parent, false)
                return CardViewHolder(view)
            }
        }
    }

    class CardComparator : DiffUtil.ItemCallback<UserProject>() {

        override fun areItemsTheSame(oldItem: UserProject, newItem: UserProject): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserProject, newItem: UserProject): Boolean {
            return oldItem.uIndex == newItem.uIndex
        }
    }
}