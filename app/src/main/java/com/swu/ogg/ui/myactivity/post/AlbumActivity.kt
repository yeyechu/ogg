package com.swu.ogg.ui.myactivity.post

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.swu.ogg.R
import java.io.IOException

class AlbumActivity : AppCompatActivity() {

    private val REQUEST_CODE_ALBUM = 1001
    private val REQUEST_CODE_PERMISSION = 1002
    private var isAlbumButtonClicked = false




    // 앨범 접근 권한 확인
    private fun checkAlbumPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openAlbum()
        } else {
            // 권한이 없는 경우 권한 요청
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION
            )
        }
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isAlbumButtonClicked) {
                    openAlbum()
                }
            } else {
                // 권한이 거부된 경우 처리
                Toast.makeText(this, "앨범 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
            isAlbumButtonClicked = false
        }
    }
    val setResultIntent = Intent()

    // 앨범 열기
    val albumLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        if (result != null) {
            val selectedImageUri: Uri = result
            setResultIntent.data = selectedImageUri
            setResult(Activity.RESULT_OK, setResultIntent)
            finish()
        }
    }

    private fun openAlbum() {
        isAlbumButtonClicked = true
        // 앨범 접근 권한 확인
        checkAlbumPermission()
        if (isAlbumButtonClicked) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            albumLauncher.launch(intent.data.toString())

        }
    }


    }
