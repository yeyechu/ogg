package com.swu.ogg

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.swu.ogg.database.*
import com.swu.ogg.databinding.ActivityMainBinding
import com.swu.ogg.member.MemberPasswordFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // DB에 현재 콘텐츠 표시를 위해 ViewModel에서 LiveData를 관찰하는 관찰자 추가
    private val newRecordActivityRequestCode = 1
    private val recordViewModel : RecordViewModel by viewModels {
        RecordViewModelFactory((application as RecordsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_env, R.id.navigation_myactivity, R.id.navigation_indicators, R.id.navigation_feed
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // ─────────────────────────────────── 플로팅 버튼 ───────────────────────────────────

        navView.background = null
        navView.menu.getItem(2).isEnabled = false

        val fab : FloatingActionButton = binding.fab
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewRecordActivity::class.java)
            startActivityForResult(intent, newRecordActivityRequestCode)
        }

        // ─────────────────────────────────── DB 리사이클러뷰 ───────────────────────────────────
        val recyclerView = binding.recyclerview
        val adapter = RecordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 관찰자 추가 -> getAlphabetizedWords가 반환하는 LiveData에 대한
        // onChanged() : 관찰된 데이터가 변경되고 Activity가 foreground에 있을 때만 실행
        recordViewModel.allRecords.observe(this, Observer { records ->
            records.let { adapter.submitList(it) }
        })
    }

    // ─────────────────────────────────── 툴바 함수 ───────────────────────────────────
    // 툴바 초기화
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 툴바 클릭 이벤트 정의
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

//        return when (item?.itemId) {
//            R.id.action_settings -> {
//                val intent = Intent(this, 멤버::class.java)
//                startActivity(intent)
//                return true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
        return super.onOptionsItemSelected(item)
    }

    // ─────────────────────────────────── 플로팅버튼 연결 ───────────────────────────────────

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if(requestCode == newRecordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringExtra(NewRecordActivity.EXTRA_REPLY)?.let {
                val record = Record(it)
                recordViewModel.insert(record)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "비어있어서 안 저장",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}