package com.swu.ogg.database.Record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.R

class RecordListAdapter : ListAdapter<Record, RecordListAdapter.RecordViewHolder>(RecordsComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.record)
    }

    class RecordViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val recordItemView : TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            recordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup) : RecordViewHolder {
                val view : View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)

                return RecordViewHolder(view)
            }
        }
    }

    // 두 단어 혹은 콘텐츠가 동일한 경우 계산하는 방법 정의
    class RecordsComparator : DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.record == newItem.record
        }
    }
}