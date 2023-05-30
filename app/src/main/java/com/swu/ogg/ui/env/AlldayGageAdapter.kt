package com.swu.ogg.ui.env

import android.graphics.Bitmap

// 전체 게이지 구현할 어댑터 정의
data class GageItem(
    val day : Int,
    val co2Left : String,
    val sticker : Bitmap
)
class AlldayGageAdapter {
}