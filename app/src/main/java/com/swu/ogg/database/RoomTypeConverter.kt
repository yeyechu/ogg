package com.swu.ogg.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

//Bitmap 타입의 image를 관리합니다.
// 실질적으로 저장되는 타입은 ByteArray로 TypeConvert를 사용하여 Bitmap -> ByteArray로 변환하여 저장합니다.
// ( Room에서는 기본형 이외의 자료를 저장하기 위해서는 TypeConvert를 사용합니다. )

//TypeConvert는 기본 자료형 이외의 자료를 저장할때 형변환을 자동으로 해주는 코드
object Converters {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}