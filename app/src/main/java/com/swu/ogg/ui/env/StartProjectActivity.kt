package com.swu.ogg.ui.env

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.swu.ogg.database.*
import com.swu.ogg.databinding.ActivityStartProjectBinding
import com.swu.ogg.ui.myactivity.TodayListAdaper

class StartProjectActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStartProjectBinding

    private val cardViewModel : CardViewModel by viewModels {
        CardViewModelFactory((application as OggApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerTest.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = TodayListAdaper()
        binding.recyclerTest.adapter = adapter

        val project1 : EditText = binding.editProject1
        val project2 : EditText = binding.editProject2
        val project3 : EditText = binding.editProject3
        val project4 : EditText = binding.editProject4
        val project5 : EditText = binding.editProject5

        cardViewModel.projectList.observe(this, Observer{
            adapter.setDate(it)
        })

        binding.btnExit.setOnClickListener {
            finish()
        }

        binding.completeBtn.setOnClickListener {
            if(TextUtils.isEmpty(project1.text)) {
                Toast.makeText(applicationContext, "안저장", Toast.LENGTH_SHORT).show()

            } else {
                val projectSet1 = project1.text.toString().toInt()
                val projectSet2 = project1.text.toString().toIntOrNull()
                val projectSet3 = project1.text.toString().toIntOrNull()
                val projectSet4 = project1.text.toString().toIntOrNull()
                val projectSet5 = project1.text.toString().toIntOrNull()
                cardViewModel.insert(UserProject(0, projectSet1, projectSet2, projectSet3, projectSet4, projectSet5))
                Toast.makeText(applicationContext, "저장", Toast.LENGTH_SHORT).show()
            }
        }
    }
    companion object {
        const val EXTRA_REPLY = "com.swu.ogg.REPLY"
    }
}