package com.swu.ogg.ui.myactivity

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.swu.ogg.database.RecordListAdapter
import com.swu.ogg.databinding.FragmentMyactivityBinding
import com.swu.ogg.dbHelper

// 나의 활동 전체 레이아웃 구현부

class MyActivityFragment : Fragment() {

    private var _binding: FragmentMyactivityBinding? = null
    private val binding get() = _binding!!

    lateinit var Image : ByteArray
    var todayList = ArrayList<CardItem>()
    var onlyList = ArrayList<CardItem>()

    lateinit var dbManager: dbHelper
    lateinit var sqlitedb: SQLiteDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myActivityViewModel =
            ViewModelProvider(this).get(MyActivityViewModel::class.java)

        _binding = FragmentMyactivityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textTitleMyactivityOne

        myActivityViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // ─────────────────────────────────── 리사이클러뷰 ───────────────────────────────────
        dbManager = dbHelper(context, "oggDB.db")

        val recyclerViewToday = binding.todayCardList
        val recyclerViewOnly = binding.onlyCardList

        sqlitedb = dbManager.readableDatabase

        todayList.clear()
        onlyList.clear()

        var cursor_today : Cursor
        cursor_today = sqlitedb.rawQuery("SELECT aTitle, cReduce, aImg FROM activityTBL, co2TBL WHERE activityTBL.aID = co2TBL.cID AND co2TBL.cCode = 'R'; ", null)

        var cursor_only : Cursor
        cursor_only = sqlitedb.rawQuery("SELECT aTitle, cReduce, aImg FROM activityTBL, co2TBL WHERE activityTBL.aID = co2TBL.cID AND co2TBL.cFreq = 0; ", null)

        while(cursor_today.moveToNext()) {
            Image = cursor_today.getBlob(cursor_today.getColumnIndexOrThrow("aImg"))
            val bitmap : Bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.size)
            var title = cursor_today.getString((cursor_today.getColumnIndexOrThrow("aTitle"))).toString()
            var co2 = cursor_today.getString((cursor_today.getColumnIndexOrThrow("cReduce"))).toString() + "kg"

            todayList.add(CardItem(bitmap, title, co2))
        }

        while(cursor_only.moveToNext()) {
            Image = cursor_only.getBlob(cursor_only.getColumnIndexOrThrow("aImg"))
            val bitmap : Bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.size)
            var title = cursor_only.getString((cursor_only.getColumnIndexOrThrow("aTitle"))).toString()
            var co2 = "매일" + cursor_only.getString((cursor_only.getColumnIndexOrThrow("cReduce"))).toString() + "kg"

            onlyList.add(CardItem(bitmap, title, co2))
        }

        myActivityViewModel.tolist.observe(viewLifecycleOwner) {

            val tolist : ArrayList<CardItem> = todayList
            val toAdapter = MyActivityAdapter(requireContext(), tolist)
            recyclerViewToday.adapter = toAdapter
        }

        myActivityViewModel.onlist.observe(viewLifecycleOwner) {

            val onlist : ArrayList<CardItem> = onlyList
            val onAdapter = MyActivityAdapter(requireContext(), onlist)
            recyclerViewOnly.adapter = onAdapter

        }

        sqlitedb.close()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
