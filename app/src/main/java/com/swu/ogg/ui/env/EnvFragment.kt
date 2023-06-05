package com.swu.ogg.ui.env

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.swu.ogg.R
import com.swu.ogg.databinding.FragmentEnvBinding
import com.swu.ogg.dbHelper

class EnvFragment : Fragment() {

    // ─────────────────────────────────── 변수 ───────────────────────────────────

    private var _binding: FragmentEnvBinding? = null
    private val binding get() = _binding!!

    lateinit var dbManager : dbHelper
    lateinit var sqlitedb : SQLiteDatabase

    var stampList = ArrayList<StampItem>()

    lateinit var stickerImage : ByteArray
    var stickerArray = ArrayList<Bitmap>()

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

        dbManager = dbHelper(context, "oggDB.db")
        sqlitedb = dbManager.readableDatabase

        stampList.clear()

//        var cursor_stamp : Cursor
//        cursor_stamp = sqlitedb.rawQuery("", null)
//
//        while (cursor_stamp.moveToNext()) {
//
//
//        }
        // 임시 데이터 생성 부분
        var x = 1
        while(x <= 21){
            stampList.add(StampItem(x++, 0f, 5))
        }

        // ────────────────────────────────── 레이아웃 트랜지션 ──────────────────────────────────

        val beforeLayout : LinearLayout = binding.beforeLayout
        val afterLayout : LinearLayout = binding.afterLayout
        val expandLayout : LinearLayout = binding.expandedLayout

        val startButton : Button = binding.btnStart
        val expandButton : ImageButton = binding.btnExpand

        // 프로젝트 시작 전 화면 트랜지션 구현할 곳
        // DB 확인 -> 진행 중인 프로젝트가 없으면 visible

        var flag : Int = 0

        if(flag == 0){
            beforeLayout.visibility = View.VISIBLE
        }

        startButton.setOnClickListener {

            beforeLayout.visibility = View.GONE
            afterLayout.visibility = View.VISIBLE
            expandButton.visibility = View.VISIBLE
        }

        // ──────────────────────────────── 프로젝트 시작 레이아웃 ────────────────────────────────

        // 레이아웃 확장 버튼 정의
        expandButton.setOnClickListener {

            if(expandLayout.visibility == View.GONE){
                expandButton.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
                expandLayout.visibility = View.VISIBLE
            } else {
                expandButton.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
                expandLayout.visibility = View.GONE
            }
        }

        val textDday: TextView = binding.tvDday

        // db 처리해서 연결
        // 며칠째인지
        // 탄소량 얼마 남았는지
        // 오늘 스티커 뭔지

        textDday.text = "21일 중 " + stampList.get(5).today.toString() + "일 째"


//        envViewModel.dDayText.observe(viewLifecycleOwner) {
//            textDday.text = "21일 중 " + it + "일 째"
//        }

        // ─────────────────────────────────── 게이지바 ───────────────────────────────────

        // 처리할 데이터 :
        // - textView : tv_co2_alarm_gage : 0.74kg 남았어요!
        // - Seekbar : determinateBar
        // - textView : tv_co2_aim_gage : 1.4kg

        val gageTextAim : TextView = binding.tvCo2AimAll
        val gageCo2Alarm : TextView = binding.tvCo2AlarmAll
        val progressBar : ProgressBar = binding.determinateBarAll

        // DB에서 받아올 데이터
        // 임시 초기화 ↓
        val gageAllAim : Float = 1.4f*21

        gageTextAim.text = gageAllAim.toString()

        // 진행률 받아와서 초기화
        // 임시 초기화 ↓
        progressBar.progress = 50

        var co2Left : Float = kotlin.math.round(gageAllAim*1000 - progressBar.progress * gageAllAim*10)/1000
        gageCo2Alarm.text = "목표 달성까지" + co2Left + "kg 남았어요"

        // ─────────────────────────────────── 스탬프 레이아웃 ───────────────────────────────────

        val gridView : GridView = binding.stampGrid

        envViewModel.stamplist.observe(viewLifecycleOwner) {

            val stamp : ArrayList<StampItem> = stampList
            val stampAdapter = StampAdapter(requireContext(), stampList)
            gridView.adapter = stampAdapter
        }

        sqlitedb.close()
        dbManager.close()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}