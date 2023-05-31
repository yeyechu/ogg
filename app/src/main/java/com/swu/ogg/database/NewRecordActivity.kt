package com.swu.ogg.database

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.swu.ogg.databinding.ActivityNewRecordBinding

class NewRecordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNewRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editRecordView : EditText = binding.editRecord
        val button : Button = binding.buttonSave
        button.setOnClickListener {

            val replyIntent = Intent()
            if(TextUtils.isEmpty(editRecordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val record = editRecordView.text.toString()
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