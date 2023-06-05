//package com.swu.ogg.ui.myactivity.post
//
//import android.Manifest
//import android.app.Activity
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Bundle
//import android.provider.MediaStore
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import com.swu.ogg.R
//
//
//import java.io.IOException
//
//class AlbumActivity : AppCompatActivity() {
//
//    private val REQUEST_IMAGE_CODE = 1001
//    private val REQUEST_EXTERNAL_STORAGE_PERMISSION = 1002
//    private lateinit var image_Preview: ImageView
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_album)
//
//        //image_Preview = findViewById(R.id.image_preview)
//
//        // 권한 확인 및 요청
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                // 권한 설명을 보여줄 필요가 있는 경우
//            } else {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    REQUEST_EXTERNAL_STORAGE_PERMISSION
//                )
//            }
//        } else {
//            // 권한이 이미 허용된 경우
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == RESULT_OK) {
//            val image: Uri? = data?.data
//            var bitmap : Bitmap? = null
//
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, image)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//
//            image_Preview.setImageBitmap(bitmap)
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//    //선택후 가져오기
//
//
//
//
//}
