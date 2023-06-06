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
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.swu.ogg.MainActivity
import com.swu.ogg.R
import com.swu.ogg.database.CardViewModel
import com.swu.ogg.database.CardViewModelFactory
import com.swu.ogg.database.OggApplication
import com.swu.ogg.databinding.FragmentMyactivityBinding
import com.swu.ogg.dbHelper


// 나의 활동 전체 레이아웃 구현부

class MyActivityFragment : Fragment() {

    private var _binding: FragmentMyactivityBinding? = null
    private val binding get() = _binding!!

    lateinit var Image : ByteArray
    var todayList = ArrayList<CardItem>()
    var onlyList = ArrayList<CardItem>()

    //lateinit var dbManager: dbHelper
    //lateinit var sqlitedb: SQLiteDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myActivityViewModel =
            ViewModelProvider(this).get(MyActivityViewModel::class.java)

        //val cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        _binding = FragmentMyactivityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNameMyactivity

        myActivityViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it + "님"
        }
        // ─────────────────────────────────── 게이지바 ───────────────────────────────────

        // 처리할 데이터 :
        // - textView : tv_co2_alarm_gage : 0.74kg 남았어요!
        // - Seekbar : determinateBar
        // - textView : tv_co2_aim_gage : 1.4kg

        val gageTextAlarm : TextView = root.findViewById(R.id.tv_co2_alarm_gage)
        val gageTextAim : TextView = root.findViewById(R.id.tv_co2_aim_gage)
        val seekbar : SeekBar = root.findViewById(R.id.determinateBar)

        // DB에서 받아올 데이터
        // 임시 초기화
        val gageAim : Float = 1.4f

        gageTextAim.text = gageAim.toString() + "kg"

        // 시크바 노터치
        //seekbar.setOnTouchListener { v, event -> true }

        // 시크바 움직임 정의
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar : SeekBar?, progress : Int, fromUser : Boolean) {

                var co2Left : Float = kotlin.math.round(gageAim*1000 - progress * gageAim*10)/1000
                gageTextAlarm.text = co2Left.toString() + "kg 남았어요"

                if(progress > 0){
                    gageTextAlarm.visibility = View.VISIBLE
                }

                val padding = seekBar!!.paddingLeft + seekBar!!.paddingRight
                val sPos = seekBar!!.left + seekBar!!.paddingLeft
                val xPos =
                    (seekBar!!.width - padding) * seekBar!!.progress / seekBar!!.max + sPos - gageTextAlarm.width/2

                gageTextAlarm.x = xPos.toFloat()
            }

            override fun onStartTrackingTouch(seekBar : SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar : SeekBar?) {
                if(seekbar.progress > 98 || seekbar.progress < 2){
                    gageTextAlarm.visibility = View.INVISIBLE
                }
            }
        })


        // ─────────────────────────────────── 리사이클러뷰 ───────────────────────────────────

        val recyclerViewToday = binding.todayCardList
        val recyclerViewOnly = binding.onlyCardList

        todayList.clear()
        onlyList.clear()


        // ViewModel에 변경을 알리는 observer 구현
        // tolist : 오늘의 활동에 대한 내용
        // onlist : 일회성 활동에 대한 내용


//        myActivityViewModel.tolist.observe(viewLifecycleOwner) {
//
//            val tolist : ArrayList<CardItem> = todayList
//            val toAdapter = MyActivityAdapter(requireContext(), tolist)
//            recyclerViewToday.adapter = toAdapter
//        }
//
//        myActivityViewModel.onlist.observe(viewLifecycleOwner) {
//
//            val onlist : ArrayList<CardItem> = onlyList
//            val onAdapter = MyActivity2Adapter(requireContext(), onlist)
//            recyclerViewOnly.adapter = onAdapter
//
//        }

      /*  dbManager = dbHelper(context, "oggDB.db")
        sqlitedb = dbManager.readableDatabase

        // DB에서 불러온 데이터 연결

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
            var co2 = cursor_only.getString((cursor_only.getColumnIndexOrThrow("cReduce"))).toString() + "kg"

            onlyList.add(CardItem(bitmap, title, co2))
        }



        cursor_today.close()
        cursor_only.close()

        sqlitedb.close()
        dbManager.close()*/

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
