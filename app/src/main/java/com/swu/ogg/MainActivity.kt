package com.swu.ogg

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.swu.ogg.database.*
import com.swu.ogg.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    val filePath : String = "/data/data/com.swu.ogg/databases/"
    lateinit var  sqlDB : SQLiteDatabase

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

        //navView.background = null
        //navView.menu.getItem(2).isEnabled = false

        val fab : FloatingActionButton = binding.fab
        fab.setOnClickListener {
//            val intent = Intent(this@MainActivity, NewRecordActivity::class.java)
//            startActivityForResult(intent, newRecordActivityRequestCode)
        }

        // ─────────────────────────────────── DB 불러오기 ───────────────────────────────────

//        var checkDB : File = File(filePath + "oggDB.db")
//        if(checkDB.exists()){
//
//        } else {
//            // sqlDB.close()
//        }
        setDB(this)
        val oggHelper : dbHelper = dbHelper(this, "oggDB.db")
        sqlDB = oggHelper.readableDatabase
    }

    // ─────────────────────────────────── 툴바 함수 ───────────────────────────────────
    // 툴바 초기화
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.top_action_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // 툴바 클릭 이벤트 정의
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    // ─────────────────────────────────── assets 폴더의 기존 DB 불러오기 ───────────────────────────────────
    private fun setDB(ctx: Context) {

        var folder: File = File(filePath)

        if (folder.exists()) {

        } else {
            folder.mkdirs();
        }

        var assetManager: AssetManager = ctx.resources.assets
        var outfile: File = File(filePath + "oggDB.db")

        var IStr: InputStream? = null
        var fo: FileOutputStream? = null
        var filesize: Int = 0

        try {
            IStr = assetManager.open("oggDB.db", AssetManager.ACCESS_BUFFER)
            filesize = IStr.available()

            if (outfile.length() <= 0) {

                val buffer = ByteArray(filesize)

                IStr.read(buffer)
                IStr.close()
                outfile.createNewFile()

                fo = FileOutputStream(outfile)
                fo.write(buffer)
                fo.close()

            } else {

            }
        } finally {

        }
    }

    // ─────────────────────────────────── DB 리사이클러뷰 관찰자 ───────────────────────────────────

//        val adapter = RecordListAdapter()
//
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        // 관찰자 추가 -> getAlphabetizedWords가 반환하는 LiveData에 대한
//        // onChanged() : 관찰된 데이터가 변경되고 Activity가 foreground에 있을 때만 실행
//        recordViewModel.allRecords.observe(this, Observer { records ->
//            records.let { adapter.submitList(it) }
//        })
}