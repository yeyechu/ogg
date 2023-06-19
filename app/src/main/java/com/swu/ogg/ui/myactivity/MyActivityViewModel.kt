package com.swu.ogg.ui.myactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swu.ogg.database.Co2All
import com.swu.ogg.database.Co2Today
import kotlinx.coroutines.delay

class MyActivityViewModel : ViewModel() {

    // ─────────────────────────────────────────────────────── 이름 업데이트
    private val _text = MutableLiveData<String>().apply {
        value = "오지지"
    }
    val text: LiveData<String> = _text

    // ─────────────────────────────────────────────────────── 카드리스트 업데이트
    private var _list = MutableLiveData<ArrayList<CardItem>>().apply {
        value = arrayListOf<CardItem>()
    }

    var tolist : LiveData<ArrayList<CardItem>> = _list
    var onlist : LiveData<ArrayList<CardItem>> = _list

    // ─────────────────────────────────────────────────────── 오늘 Co2
    private val _float = MutableLiveData<Float>().apply {
        value = Co2Today.getCo2Today()
    }
    val float : LiveData<Float> = _float

    fun increaseToday(result : Float) {
        _float.value = _float.value?.plus(result)
    }

    // ─────────────────────────────────────────────────────── 오늘 게이지
    private var _progress = MutableLiveData<Int>().apply {
        value = ((Co2Today.getCo2Today() / 1.4f)*100).toInt()
    }
    val process : LiveData<Int> = _progress

    fun processSet(result : Int){
        _progress.value = _progress.value?.plus(result)
    }

    // ─────────────────────────────────────────────────────── 전체 게이지
//    private val _allday = MutableLiveData<Float>().apply {
//        value = Co2All.getCo2All()
//    }
//
//    val allday: LiveData<Float> = _allday
//
//    fun increaseAll(result : Float) {
//        _allday.value = _allday.value?.plus(result)
//    }



}
