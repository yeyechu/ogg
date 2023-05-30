package com.swu.ogg.ui.env

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// DB나 서버 통신으로 받아온 데이터 관리하는 곳
// 데이터 변경이 감지되는 대로 UI 컴포넌트의 바인딩된 뷰에 나타냄
// 서버에서 얻은 데이터는 내부 데이터베이스에 저장하여 불러옴
//
class EnvViewModel : ViewModel() {

    private val db1Text = MutableLiveData<String>().apply {
        value = "11"
    }
    private val db2Text = MutableLiveData<String>().apply {
        value = "1.05"
    }
    val dDayText: LiveData<String> = db1Text
    val co2Text : LiveData<String> = db2Text
}