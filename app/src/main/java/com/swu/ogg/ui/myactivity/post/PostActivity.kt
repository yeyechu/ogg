package com.swu.ogg.ui.myactivity.post

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.swu.ogg.R
import com.swu.ogg.database.Co2Today
import com.swu.ogg.databinding.ActivityPostBinding
import com.swu.ogg.dbHelper
import java.io.IOException

class PostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPostBinding

    lateinit var dbManager : dbHelper
    lateinit var sqlitedb : SQLiteDatabase
    lateinit var image : ByteArray

    var imgArray = ArrayList<Bitmap>()

    private val REQUEST_IMAGE_CODE = 1
    var imgArr : Array<Bitmap?> = arrayOfNulls<Bitmap?>(1)

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

        dbManager = dbHelper(this)
        sqlitedb = dbManager.readableDatabase

        val co2Text : TextView = binding.tvCo2
        val freqText : TextView = binding.tvContentFreq

        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM TodayTBL WHERE aTitle = '" + extraTitle + "' ;", null)
        var activityNum : String = ""
        var activityCo2 : String = ""

        while(cursor.moveToNext()){
            image = cursor.getBlob(cursor.getColumnIndexOrThrow("aImg"))
            val bitmap : Bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
            activityNum = cursor.getString(cursor.getColumnIndexOrThrow("aID")).toString()

            val co2 = cursor.getString(cursor.getColumnIndexOrThrow("aCo2")).toString() + "kg"
            val freq = cursor.getString(cursor.getColumnIndexOrThrow("aFreq")).toString()
            val limit = cursor.getString(cursor.getColumnIndexOrThrow("aLimit")).toString()
            activityCo2 = cursor.getString(cursor.getColumnIndexOrThrow("aCo2")).toString()

            Log.d("activityCo2 설정 : ", activityCo2)

            co2Text.text = co2
            if(limit != null && freq == "1") {

                freqText.text = "$limit 일에 $freq 번"

            } else if(limit != null && freq == "2"){

                freqText.text = "하루 " + limit + "번"

            } else if(freq == "0"){

                freqText.text = "단 한 번!"
            }else{

                freqText.text = "하루 " + freq + "번"
            }

            imgArray.add(bitmap)

            var gallery = cursor.getInt(cursor.getColumnIndexOrThrow("aGallery"))

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

        // ─────────────────────────────────── 포스팅 버튼 ───────────────────────────────────

        postButton.setOnClickListener {

            //Co2Today.addCo2Today(activityCo2.toFloat())
            Co2Today.addCo2Today(activityCo2.toFloat())
            //Co2All.addCo2All(activityCo2.toString().toFloat())

            dbManager = dbHelper(this)
            sqlitedb = dbManager.writableDatabase

            var cursor: Cursor
            cursor = sqlitedb.rawQuery("SELECT * FROM post;",null)

            while (cursor.moveToNext()){

                sqlitedb.execSQL("UPDATE post SET pCo2Today = '"
                        + Co2Today.getCo2Today() + "' WHERE pID='"
                        + 1 + "';")
            }

            Log.d("setCo2To()", Co2Today.getCo2Today().toString())

            cursor.close()
            sqlitedb.close()
            dbManager.close()

            finish()
        }

        sqlitedb.close()
        dbManager.close()
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
                binding.imagePreview.setImageBitmap(CameraActivity().rotateBitmap(bitmap))

                imgArr[0] = bitmap
            }
        }
    }
}
