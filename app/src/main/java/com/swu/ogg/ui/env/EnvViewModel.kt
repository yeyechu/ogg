package com.swu.ogg.ui.env

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swu.ogg.database.Co2All
import com.swu.ogg.database.Co2Today
import com.swu.ogg.database.DateSet


// DB나 서버 통신으로 받아온 데이터 관리하는 곳
// 데이터 변경이 감지되는 대로 UI 컴포넌트의 바인딩된 뷰에 나타냄
// 서버에서 얻은 데이터는 내부 데이터베이스에 저장하여 불러옴
class EnvViewModel : ViewModel() {

    private val _today = MutableLiveData<Int>().apply {
        value = DateSet.getDateToday()
    }
    val today: LiveData<Int> = _today

    fun update() {
        _today.value = _today.value?.plus(1)
    }

    // ───────────────────────────── 스탬프 ─────────────────────────────

    private var _list2 = MutableLiveData<ArrayList<StampItem>>().apply {
        value = arrayListOf<StampItem>()
    }
    var stamplist : LiveData<ArrayList<StampItem>> = _list2

    // ───────────────────────────── 게이지 ─────────────────────────────

    private var _progress = MutableLiveData<Int>().apply {

        value = (Co2All.getCo2All()/1.4f*21 * 100).toInt()
    }
    val process : LiveData<Int> = _progress

    fun processSet(result : Int){

        _progress.value = _progress.value?.plus(result)
    }

    // ───────────────────────────── Co2 ─────────────────────────────

    private var _co2all = MutableLiveData<Float>().apply {

        value = Co2All.getCo2All()
    }
    val co2all : LiveData<Float> = _co2all

    fun addCo2(result : Float) {

        _co2all.value = _co2all.value?.plus(result)
    }
}