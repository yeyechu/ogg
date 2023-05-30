package com.swu.ogg.ui.myactivity.post

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.swu.ogg.R
import com.swu.ogg.databinding.ActivityPostBinding
import com.swu.ogg.dbHelper

class PostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPostBinding

    lateinit var dbManager : dbHelper
    lateinit var sqlitedb : SQLiteDatabase
    lateinit var Image : ByteArray

    var imgArray = ArrayList<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ─────────────────────────────────── 받아온 값 ───────────────────────────────────

        var extraTitle = intent.getStringExtra("titleActivity")

        val textTitle : TextView = binding.tvTitle
        textTitle.text = extraTitle

        // ─────────────────────────────────── db 연결 ───────────────────────────────────

        val exitButton : ImageButton = binding.btnExit
        exitButton.setOnClickListener {
            finish()
        }

        dbManager = dbHelper(this, "oggDB.db")
        sqlitedb = dbManager.readableDatabase

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT aID, aImg FROM activityTBL WHERE aTitle = '" + extraTitle + "' ;", null)
        var activityNum : String = ""
        var activityCo2 : String = ""

        while(cursor.moveToNext()){
            Image = cursor.getBlob(cursor.getColumnIndexOrThrow("aImg"))
            val bitmap : Bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.size)
            activityNum = cursor.getString(cursor.getColumnIndexOrThrow("aID")).toString()

            imgArray.add(bitmap)
        }

        val imageView : ImageView = binding.imgView
        imageView.setImageBitmap(imgArray[0])
        cursor.close()

        // ───────────────────────────── 오른쪽 왼쪽 사진 넘어가는 버튼 ─────────────────────────────
        var size : Int = imgArray.size
        var cursor_img : Int = 0

        val leftButton : ImageButton = binding.btnLeft
        val rightButton : ImageButton = binding.btnRight

        leftButton.isEnabled = false
        rightButton.isEnabled = false

        // leftButton : 보여줄 사진 있을 때만 isEnabled = true 처리
        // rightButton : 보여줄 사진 있을 때만 isEnabled = true 처리

        leftButton.setOnClickListener {
            cursor_img++
            if(cursor_img > size - 1) {
                cursor_img = 0
            }
            imageView.setImageBitmap(imgArray[cursor_img])
        }

        rightButton.setOnClickListener {
            cursor_img--
            if(cursor_img < 0) {
                cursor_img = size -1
            }
            imageView.setImageBitmap(imgArray[cursor_img])
        }
        // ─────────────────────────────────── 인증 가능 횟수 ───────────────────────────────────

        var cursor_c: Cursor
        cursor_c = sqlitedb.rawQuery("SELECT * FROM co2TBL WHERE aID = '" + activityNum + "' ;", null)

        while(cursor_c.moveToNext()) {
            var co2 = cursor_c.getString(cursor_c.getColumnIndexOrThrow("cReduce")).toString() + "kg"
            var freq = cursor_c.getString(cursor_c.getColumnIndexOrThrow("cFreq")).toString()
            var limit = cursor_c.getString(cursor_c.getColumnIndexOrThrow("cLimit")).toString()
            activityCo2 = cursor_c.getString(cursor_c.getColumnIndexOrThrow("cReduce")).toString()

            val co2Text : TextView = binding.tvCo2
            val freqText : TextView = binding.tvContentFreq

            co2Text.text = co2
            if(limit != null && freq == "1") {

                freqText.text = "$limit 일에 $freq 번"
            } else if(limit != null && freq == "2"){

                freqText.text = "하루 " + limit + "번"
            } else{

                freqText.text = "하루 " + freq + "번"
            }
        }

        cursor_c.close()

        // ─────────────────────────────────── 카메라 버튼 ───────────────────────────────────
        val buttonCamera : Button = binding.btnCamera
        buttonCamera.setOnClickListener{

            var intent : Intent = Intent(this, CameraActivity::class.java)
            intent.putExtra("titleActivity", extraTitle)
            intent.putExtra("co2Activity", activityCo2)
            this.startActivity(intent)
            finish()

        }

        // ─────────────────────────────────── 갤러리 버튼 ───────────────────────────────────
        var cursor_b : Cursor
        val REQUEST_IMAGE_CODE = 1001
        cursor_b = sqlitedb.rawQuery("SELECT gGallery FROM guideTBL WHERE aID = '" + activityNum + "' ;", null)
        while (cursor_b.moveToNext()) {
            var gallery = cursor_b.getInt(cursor_b.getColumnIndexOrThrow("gGallery")).toInt()

            if(gallery == 1) {
                val buttonAlbum : Button = binding.btnAlbum
                buttonAlbum.isEnabled = true
                //색 바뀌게 코드


                buttonAlbum.setOnClickListener {
                    // 앨범 연결 부분
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                    startActivityForResult(intent,REQUEST_IMAGE_CODE)

                    finish()

                }
            }
        }
        cursor_b.close()
    }
}
