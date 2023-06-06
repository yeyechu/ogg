package com.swu.ogg.ui.myactivity.post

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.swu.ogg.R
import com.swu.ogg.databinding.ActivityPostBinding
import com.swu.ogg.dbHelper
import java.io.IOException

class PostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPostBinding

    lateinit var dbManager : dbHelper
    lateinit var sqlitedb : SQLiteDatabase
    lateinit var Image : ByteArray

    var imgArray = ArrayList<Bitmap>()

    private val REQUEST_IMAGE_CODE = 1
    var imgArr : Array<Bitmap?> = arrayOfNulls<Bitmap>(1)

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

        val co2Text : TextView = binding.tvCo2
        val freqText : TextView = binding.tvContentFreq

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM TodayTBL WHERE aTitle = '" + extraTitle + "' ;", null)
        var activityNum : String = ""
        var activityCo2 : String = ""

        while(cursor.moveToNext()){
            Image = cursor.getBlob(cursor.getColumnIndexOrThrow("aGuide"))
            val bitmap : Bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.size)
            activityNum = cursor.getString(cursor.getColumnIndexOrThrow("aID")).toString()

            val co2 = cursor.getString(cursor.getColumnIndexOrThrow("aCo2")).toString() + "kg"
            val freq = cursor.getString(cursor.getColumnIndexOrThrow("aFreq")).toString()
            val limit = cursor.getString(cursor.getColumnIndexOrThrow("aLimit")).toString()
            activityCo2 = cursor.getString(cursor.getColumnIndexOrThrow("aCo2")).toString()

            co2Text.text = co2
            if(limit != null && freq == "1") {

                freqText.text = "$limit 일에 $freq 번"
            } else if(limit != null && freq == "2"){

                freqText.text = "하루 " + limit + "번"
            } else{

                freqText.text = "하루 " + freq + "번"
            }

            imgArray.add(bitmap)

            var gallery = cursor.getInt(cursor.getColumnIndexOrThrow("aGallery")).toInt()

            if(gallery == 1) {
                val buttonAlbum : Button = binding.btnAlbum
                buttonAlbum.visibility = View.VISIBLE
                buttonAlbum.isEnabled = true
                //색 바뀌게 코드
                buttonAlbum.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.Primary_blue))
                buttonAlbum.setBackgroundResource(R.drawable.box_lineblue) // 배경 리소스 설정

                buttonAlbum.setOnClickListener {
                    // 앨범 연결 부분
                    openGallery()
                }
            }
        }

        val imageView : ImageView = binding.imgView
        imageView.setImageBitmap(imgArray[0])
        cursor.close()

        // ───────────────────────────── 오른쪽 왼쪽 사진 넘어가는 버튼 ─────────────────────────────
//        var size : Int = imgArray.size
//        var cursor_img : Int = 0
//
//        val leftButton : ImageButton = binding.btnLeft
//        val rightButton : ImageButton = binding.btnRight
//
//        leftButton.isEnabled = false
//        rightButton.isEnabled = false

        // leftButton : 보여줄 사진 있을 때만 isEnabled = true 처리
        // rightButton : 보여줄 사진 있을 때만 isEnabled = true 처리

//        leftButton.setOnClickListener {
//            cursor_img++
//            if(cursor_img > size - 1) {
//                cursor_img = 0
//            }
//            imageView.setImageBitmap(imgArray[cursor_img])
//        }
//
//        rightButton.setOnClickListener {
//            cursor_img--
//            if(cursor_img < 0) {
//                cursor_img = size -1
//            }
//            imageView.setImageBitmap(imgArray[cursor_img])

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

        val retakeButton : Button = binding.btnRetake
        retakeButton.text = "다시 고르기"
        val postButton : Button = binding.btnPost

        retakeButton.setOnClickListener {
            binding.previewLayout.visibility = View.GONE
            openGallery()
        }
        postButton.setOnClickListener {

            // db 처리
            // 인증 등록 사진 db 저장 후
            // 인증 완료 비활성화 처리 필요한 부분 구현 필요

            // LiveData observe 구현 필요
            // -> 오늘 게이지
            // -> 전체 게이지
            // -> 활동탭 리스트
            // -> 환경
            // -> 환경탭 스티커
            // -> 피드(2학기)

            val replyIntent = Intent()
            if(false) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val result = "여기에 보낼 데이터를 저장하여 보냄"
                replyIntent.putExtra(CameraActivity.EXTRA_REPLY, result)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    private fun openGallery(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, REQUEST_IMAGE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            1 -> if (resultCode == RESULT_OK) {
                val image: Uri? = data?.data
                var bitmap : Bitmap? = null

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, image)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                binding.previewLayout.visibility = View.VISIBLE
                //binding.imagePreview.setImageBitmap(bitmap)
                binding.imagePreview.setImageBitmap(CameraActivity().rotateBitmap(bitmap))

                imgArr[0] = bitmap
            }
        }
    }
}
