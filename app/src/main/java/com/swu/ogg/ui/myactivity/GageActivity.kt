package com.swu.ogg.ui.myactivity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

// 오늘 게이지 구현할 어댑터 정의
// 처리할 데이터 :
// - textView : tv_co2_alarm_gage : 0.74kg 남았어요!
// - Seekbar : determinateBar
// - textView : tv_co2_aim_gage : 1.4kg
// 전체 게이지에 대한 데이터 클래스
data class GageItem(
    val co2Left : String,
    val aim : String,
    val percent : Float
)

class GagetSet {

    fun getGage(aim : String, co2left : String) : String {

        return ""
    }
}