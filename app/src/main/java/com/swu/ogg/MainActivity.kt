package com.swu.ogg

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.swu.ogg.databinding.ActivityMainBinding
import com.swu.ogg.member.MemberPasswordFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        fab.setOnClickListener { view ->
            Snackbar.make(view, "~~", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
        }
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

}