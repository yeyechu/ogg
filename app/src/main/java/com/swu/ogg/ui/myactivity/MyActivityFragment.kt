package com.swu.ogg.ui.myactivity

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.swu.ogg.R
import com.swu.ogg.database.Co2Today
import com.swu.ogg.databinding.FragmentMyactivityBinding
import com.swu.ogg.dbHelper


// 나의 활동 전체 레이아웃 구현부

class MyActivityFragment : Fragment() {

    private var _binding: FragmentMyactivityBinding? = null
    private val binding get() = _binding!!

    lateinit var dbManager: dbHelper
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var Image : ByteArray

    var todayList = ArrayList<CardItem>()
    var onlyList = ArrayList<CardItem>()
    var co2Convert = Int

    var gageAim = 1.4f

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

        // ───────────────────────────────── 사용자 이름 초기화 ─────────────────────────────────

        val textView: TextView = binding.textNameMyactivity

        myActivityViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it + "님"
        }
        // ───────────────────────────────── 시크바 초기화 ─────────────────────────────────

        val gageTextAlarm : TextView = root.findViewById(R.id.tv_co2_alarm_gage)
        val gageTextAim : TextView = root.findViewById(R.id.tv_co2_aim_gage)
        val seekbar : SeekBar = root.findViewById(R.id.determinateBar)

//        // 시크바 노터치
//        seekbar.setOnTouchListener { v, event -> false }
//        //시크바 View / 변경된 값 / 사용자에 의한 변경인지(True), 코드에 의한 변경인지(False)
//        gageTextAlarm.setOnTouchListener { v, event -> false }

        // DB값 받아오기
        dbManager = dbHelper(requireContext())
        sqlitedb = dbManager.readableDatabase
        sqlitedb = dbManager.writableDatabase

        getCo2To()

        // 프로세스값 초기화
        gageTextAim.text = gageAim.toString() + "kg"
        var co2Converter = ((Co2Today.getCo2Today() / gageAim) * 100).toInt()

        myActivityViewModel.processSet(co2Converter)

        myActivityViewModel.process.observe(viewLifecycleOwner){

            co2Converter = ((Co2Today.getCo2Today() / gageAim) * 100).toInt()
            seekbar.progress = co2Converter
            Log.d("시크바 뷰모델 process", seekbar.progress.toString())
        }

        myActivityViewModel.float.observe(viewLifecycleOwner) {

            Co2Today.setCo2Today(it)
            Log.d("시크바 뷰모델 co2", Co2Today.getCo2Today().toString())
            Log.d("시크바 뷰모델 float", seekbar.progress.toString())
        }

//        myActivityViewModel.allday.observe(viewLifecycleOwner) {
//
//            //Co2All.setCo2All(it)
//            Log.d("시크바 뷰모델 co2All", Co2All.getCo2All().toString())
//        }

        // ───────────────────────────────── 시크바 정의 ─────────────────────────────────

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            // 움직임 진행 중
            override fun onProgressChanged(seekBar : SeekBar?, progress : Int, fromUser : Boolean) {

                seekbar.progress = co2Converter.toInt()

                var co2Left = gageAim - Co2Today.getCo2Today()
                co2Left = kotlin.math.round(co2Left*1000)/1000
                gageTextAlarm.text = co2Left.toString() + "kg 남음"

                // 시크바 노터치
                seekbar.setOnTouchListener { v, event -> false }
                //시크바 View / 변경된 값 / 사용자에 의한 변경인지(True), 코드에 의한 변경인지(False)
                gageTextAlarm.setOnTouchListener { v, event -> false }

                // 시크바 말풍선 위치 설정
                if(progress > 0){
                    gageTextAlarm.visibility = View.VISIBLE
                }

                val padding = seekBar!!.paddingLeft + seekBar!!.paddingRight
                val sPos = seekBar!!.left + seekBar!!.paddingLeft
                val xPos =
                    (seekBar!!.width - padding) * seekBar!!.progress / seekBar!!.max + sPos - gageTextAlarm.width/2

                gageTextAlarm.x = xPos.toFloat()

            }

            // 움직임 시작
            override fun onStartTrackingTouch(seekBar : SeekBar?) {
            }

            // 움직임 종료
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

        var cursor_today : Cursor
        cursor_today = sqlitedb.rawQuery("SELECT aTitle, aCo2, aImg FROM TodayTBL; ", null)

        while(cursor_today.moveToNext()) {
            Image = cursor_today.getBlob(cursor_today.getColumnIndexOrThrow("aImg"))
            val bitmap : Bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.size)
            var title = cursor_today.getString((cursor_today.getColumnIndexOrThrow("aTitle"))).toString()
            var co2 = cursor_today.getString((cursor_today.getColumnIndexOrThrow("aCo2"))).toString() + "kg"

            todayList.add(CardItem(bitmap, title, co2))
        }

        cursor_today.close()

        var cursor_only : Cursor
        cursor_only = sqlitedb.rawQuery("SELECT oTitle, oCo2, oImg FROM OnlyTBL; ", null)

        while(cursor_only.moveToNext()) {
            Image = cursor_only.getBlob(cursor_only.getColumnIndexOrThrow("oImg"))
            val bitmap : Bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.size)
            var title = cursor_only.getString((cursor_only.getColumnIndexOrThrow("oTitle"))).toString()
            var co2 = cursor_only.getString((cursor_only.getColumnIndexOrThrow("oCo2"))).toString() + "kg"

            onlyList.add(CardItem(bitmap, title, co2))
        }
        cursor_only.close()

        myActivityViewModel.tolist.observe(viewLifecycleOwner) {

            val tolist : ArrayList<CardItem> = todayList
            val toAdapter = MyActivityAdapter(requireContext(), tolist)
            recyclerViewToday.adapter = toAdapter
        }

        myActivityViewModel.onlist.observe(viewLifecycleOwner) {

            val onlist : ArrayList<CardItem> = onlyList
            val onAdapter = MyActivity2Adapter(requireContext(), onlist)
            recyclerViewOnly.adapter = onAdapter
        }

        return root
    }

    fun getCo2To() {

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM post;", null)

        while(cursor.moveToNext()){

            var co2mount = cursor.getString((cursor.getColumnIndexOrThrow("pCo2Today")))

            Co2Today.setCo2Today(co2mount.toFloat())
        }

        cursor.close()

        Log.d("getCo2To()", Co2Today.getCo2Today().toString())
    }

    fun setCo2To() {

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM post;",null)

        while (cursor.moveToNext()){

            sqlitedb.execSQL("UPDATE post SET pCo2Today = '"
                    + Co2Today.getCo2Today() + "' WHERE pID='"
                    + 1 + "';")
        }

        Log.d("setCo2To()", Co2Today.getCo2Today().toString())

        cursor.close()
    }

    override fun onDestroyView() {

        setCo2To()
        sqlitedb.close()
        dbManager.close()

        super.onDestroyView()
        _binding = null
    }

}
