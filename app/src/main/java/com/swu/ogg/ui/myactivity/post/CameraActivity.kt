package com.swu.ogg.ui.myactivity.post

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.swu.ogg.databinding.ActivityCameraBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class CameraActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCameraBinding
    private var imageCapture : ImageCapture? = null
    private lateinit var cameraExecutor : ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ─────────────────────────────────── 받아온 값 출력 ───────────────────────────────────

        var extraTitle = intent.getStringExtra("titleActivity")
        var extraCo2 = intent.getStringExtra("co2Activity")

        val textTitle : TextView = binding.tvTitle
        val textCo2 : TextView = binding.tvCo2

        textTitle.text = extraTitle
        textCo2.text = extraCo2

        // ─────────────────────────────────── 버튼 정의 ───────────────────────────────────

        val exitButton : ImageButton = binding.btnExit
        val cameraButton : ImageButton = binding.btnCamera

        val preview  = binding.previewLayout
        val retakeButton : Button = binding.btnRetake
        val postButton : Button = binding.btnPost

        exitButton.setOnClickListener {
            finish()
        }

        retakeButton.setOnClickListener {
            preview.visibility = View.GONE
        }
        postButton.setOnClickListener {

            // db 처리
            // 인증 등록 사진 db 저장 후

            // LiveData observe 구현 필요
            // -> 오늘 게이지
            // -> 전체 게이지
            // -> 활동탭 리스트
            // -> 환경
            // -> 환경탭 스티커
            // -> 피드(2학기)

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

        // ─────────────────────────────────── 카메라 정의 ───────────────────────────────────

        if(allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        cameraButton.setOnClickListener {
            takePhoto()
            preview.visibility = View.VISIBLE
        }
        cameraExecutor = Executors.newSingleThreadExecutor()

    }

    // 사진 찍기
    private fun takePhoto() {

        // imageCapture 사용 사례에 대한 참조 불러오기 -> null이면 함수 종료
        val imageCapture = imageCapture ?: return

        var bitmap : Bitmap? = null

        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.KOREA).format(System.currentTimeMillis())

        val contentValues = ContentValues().apply {

            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/오늘도 지구를 지켰다")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "사진 찍기 실패 : ${exception.message}", exception)
                }

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val msg = "사진 찍기 성공 : ${outputFileResults.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, msg)

                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, outputFileResults.savedUri)
                    binding.imagePreview.setImageBitmap(bitmap)
                }
            }
        )

    }

    // 미리보기 화면
    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({

            // 카메라 lifercycle과 카메라 소유자의 lifecycle 같게하기 -> 카메라 열고 닫는 작업을 따로 하지 않아도 됨
            //
            val cameraProvider : ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try{
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc : Exception) {
                Log.e(TAG, "바인딩 실패", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    // 카메라 권한
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_CODE_PERMISSIONS) {
            startCamera()
        } else {
            Toast.makeText(this,
                "카메라 권한 필요",
                Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {

        const val EXTRA_REPLY = "com.swu.ogg.recordlistsql.REPLY"

        private const val TAG = "Camera"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = (
                mutableListOf(
                    Manifest.permission.CAMERA
                ).apply {
                    if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                        add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        add(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }.toTypedArray()
                )
    }
}