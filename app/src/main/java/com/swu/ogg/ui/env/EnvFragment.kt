package com.swu.ogg.ui.env

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.swu.ogg.R
import com.swu.ogg.database.DateSet
import com.swu.ogg.databinding.FragmentEnvBinding
import com.swu.ogg.dbHelper
import com.swu.ogg.database.Co2All
import com.swu.ogg.database.Co2History
import com.swu.ogg.database.Co2Today

class EnvFragment : Fragment() {

    // ─────────────────────────────────── 변수 ───────────────────────────────────

    private var _binding: FragmentEnvBinding? = null
    private val binding get() = _binding!!

    lateinit var dbManager : dbHelper
    lateinit var sqlitedb : SQLiteDatabase

    var stampList = ArrayList<StampItem>()
    var co2List = ArrayList<Co2History>()

    lateinit var dayButton : Button

    // ────────────────────────────────────────────────────────────────────────────

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val envViewModel =
            ViewModelProvider(this).get(EnvViewModel::class.java)

        _binding = FragmentEnvBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val envToolbar = binding.envToolbar
        envToolbar.inflateMenu(R.menu.env_menu)

        // ────────────────────────────────── 데이터베이스 준비 ──────────────────────────────────

        dbManager = dbHelper(requireContext())
        sqlitedb = dbManager.readableDatabase
        sqlitedb = dbManager.writableDatabase

        stampList.clear()
        co2List.clear()

        getCo2()
        getGage()
        setGage()

        // ────────────────────────────────── 레이아웃 트랜지션 ──────────────────────────────────

        val beforeLayout : LinearLayout = binding.beforeLayout
        val afterLayout : LinearLayout = binding.afterLayout

        val startButton : Button = binding.btnStart
        val expandButton : ImageButton = binding.btnExpand
        val stampLayout : GridView = binding.stampGrid

        val todaySticker : ImageView = binding.imageTodaySticker
        val imageChange : ImageView = root.findViewById(R.id.start_env_image)

        val textDday: TextView = binding.tvDday

        val gageTextAim : TextView = binding.tvCo2AimAll
        val gageCo2Alarm : TextView = binding.tvCo2AlarmAll
        val progressBar : ProgressBar = binding.determinateBarAll

        var actionDate = 1
        var co2Left : Float
        val gageAllAim : Float = 1.4f*21

        gageTextAim.text = gageAllAim.toString()

        // 프로젝트 시작 전 화면 트랜지션 구현할 곳

        if(DateSet.date == 0){

            beforeLayout.visibility = View.VISIBLE
            DateSet.setDateToday(1)

        } else {
            beforeLayout.visibility = View.GONE
            afterLayout.visibility = View.VISIBLE
            expandButton.visibility = View.VISIBLE
            imageChange.setImageResource(R.drawable.prototypebackground_bad)
        }

        startButton.setOnClickListener {

            envViewModel.update()

            beforeLayout.visibility = View.GONE
            afterLayout.visibility = View.VISIBLE
            expandButton.visibility = View.VISIBLE
            imageChange.setImageResource(R.drawable.prototypebackground_bad)

            for( i in 0..20) {
                setCo2(i, "0")
            }
            getCo2()
            //getGage()
            //setGage()
        }

        envViewModel.today.observe(viewLifecycleOwner) {

            textDday.text = "21일 중 " + it.toString() + "일 째"
            DateSet.setDateToday(it)

            setSticker(todaySticker)
            getGage()
            setGage()

            envViewModel.addCo2(Co2Today.getCo2Today())

            co2Left = gageAllAim - Co2All.getCo2All()
            co2Left = kotlin.math.round(co2Left*1000)/1000
            gageCo2Alarm.text = "21일 목표 탄소량까지 " + co2Left.toString() +"kg 남았어요"
            Log.d("co2Left", co2Left.toString())

            envViewModel.processSet((Co2Today.getCo2Today()/gageAllAim * 100).toInt())
        }

        // ──────────────────────────────── 프로젝트 시작 레이아웃 ────────────────────────────────

        // 레이아웃 확장 버튼 정의
        expandButton.setOnClickListener {

            if(stampLayout.visibility == View.GONE){
                expandButton.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                stampLayout.visibility = View.VISIBLE

            } else {
                expandButton.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                stampLayout.visibility = View.GONE
            }
        }

        // ─────────────────────────────────── 게이지바 ───────────────────────────────────

        envViewModel.co2all.observe(viewLifecycleOwner) {

            co2Left = gageAllAim - it
            co2Left = kotlin.math.round(co2Left*1000)/1000
            gageCo2Alarm.text = "21일 목표 탄소량까지 " + co2Left.toString() +"kg 남았어요"
            Log.d("co2Left", co2Left.toString())

            Co2All.setCo2All(it/2)
            Log.d("co2all observe", Co2All.getCo2All().toString())
            Log.d("co2all observe it", (it/2).toString())
        }

        envViewModel.process.observe(viewLifecycleOwner) {

            progressBar.progress = it/2
            Log.d("progress observe", (it/2).toString())
        }

        // ─────────────────────────────────── 스탬프 레이아웃 ───────────────────────────────────

        val gridView : GridView = binding.stampGrid

        actionDate = 1
        while(actionDate <= 21){

            var temp = co2List[actionDate - 1].co2
            stampList.add(StampItem(actionDate++, temp, DateSet.getDateToday()))
        }

        envViewModel.stamplist.observe(viewLifecycleOwner) {

            val stampAdapter = StampAdapter(requireContext(), stampList)
            gridView.adapter = stampAdapter
        }

        // ─────────────────────────────────── 날짜 버튼 ───────────────────────────────────

        dayButton = root.findViewById(R.id.btn_day)
        var index = 1

        dayButton.setOnClickListener {

            if(DateSet.getDateToday() < 21) {

                actionDate = 1

                var co2Temp = Co2Today.getCo2Today().toString()
                setCo2(DateSet.getDateToday(), co2Temp)
                resetCo2()

                envViewModel.update()

                while(actionDate <= 21){

                    index = actionDate - 1

                    var temp = co2List[index].co2
                    stampList.set(index, StampItem(actionDate++, temp, DateSet.getDateToday()))
                }

                val stampAdapter = StampAdapter(requireContext(), stampList)
                gridView.adapter = stampAdapter

                Co2Today.setCo2Today(0f)
                todaySticker.setImageResource(R.drawable.calendersticker_1)
            }
        }

        return root
    }

    // ──────────────────────────────── 툴바 메뉴 정의 ────────────────────────────────
    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.navigation_settings -> {
            //findNavController().navigate(R.id.action_navigation_env_to_navigation_settings2)
            true
        }
        R.id.navigation_collections -> {
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun setSticker(todaySticker : ImageView) {

        when(Co2Today.getCo2Today()){

            // 하나도 못했을 때 스탬프
            0f -> todaySticker.setImageResource(R.drawable.calendersticker_1)

            // 50%일 때 스탬프
            in 0.001f..0.7f -> todaySticker.setImageResource(R.drawable.calendersticker_2)

            // 100% 이상일 때 스탬프
            else -> todaySticker.setImageResource(R.drawable.calendersticker_3)
        }

    }
    fun getCo2() {
        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM co2HistoryTBL;",null)

        while(cursor.moveToNext())
        {
            var co2mount = cursor.getString((cursor.getColumnIndexOrThrow("co2Mount")))

            co2List.add(Co2History(co2mount))
        }

        cursor.close()
    }

    fun setCo2(index : Int, co2 : String) {

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM co2HistoryTBL;",null)

        while (cursor.moveToNext()){

            sqlitedb.execSQL("UPDATE co2HistoryTBL SET co2Mount = '"
                    + co2 + "' WHERE co2Index='"
                    + index + "';")
        }
    }

    fun resetCo2() {

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM co2HistoryTBL;",null)

        var index = 0
        while(cursor.moveToNext())
        {
            var co2mount = cursor.getString((cursor.getColumnIndexOrThrow("co2Mount")))

            co2List.set(index, Co2History(co2mount))
            index++
        }

        cursor.close()

    }

    fun getGage() {

        var summation = 0f
        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM co2HistoryTBL;", null)

        while(cursor.moveToNext()){

            var co2mount = cursor.getString((cursor.getColumnIndexOrThrow("co2Mount")))

            summation += co2mount.toFloat()
        }

        Co2All.setCo2All(summation)
        cursor.close()
        Log.d("getGage()", Co2All.getCo2All().toString())
    }

    fun setGage() {

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM post;",null)

        while (cursor.moveToNext()){

            sqlitedb.execSQL("UPDATE post SET pCo2All = '"
                    + Co2All.getCo2All() + "' WHERE pID='"
                    + 1 + "';")
        }

        Log.d("setGage()", Co2All.getCo2All().toString())
        cursor.close()
    }

    override fun onDestroyView() {

        sqlitedb.close()
        dbManager.close()

        super.onDestroyView()
        _binding = null
    }
}