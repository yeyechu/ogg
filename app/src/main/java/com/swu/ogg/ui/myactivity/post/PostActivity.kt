package com.swu.ogg.ui.myactivity.post

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.swu.ogg.databinding.ActivityPostBinding
import com.swu.ogg.dbHelper

class PostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPostBinding

    lateinit var dbManager : dbHelper
    lateinit var sqlitedb : SQLiteDatabase
    lateinit var Image : ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var extraTitle = intent.getStringExtra("titleActivity")
        dbManager = dbHelper(this, "oggDB.db")

        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM activityTBL WHERE aTitle = '" + extraTitle + "' ;", null)

        val title : TextView = binding.tvTitle
        title.text = extraTitle

        val buttonCamera : Button = binding.btnCamera
        buttonCamera.setOnClickListener{

            val replyIntent = Intent()
            if(true) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val record = " "
                replyIntent.putExtra(EXTRA_REPLY, record)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()

        }
    }

    companion object {
        const val EXTRA_REPLY = "com.swu.ogg.recordlistsql.REPLY"
    }
}
