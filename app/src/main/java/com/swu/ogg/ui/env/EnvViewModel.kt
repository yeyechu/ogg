package com.swu.ogg.ui.env

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swu.ogg.database.Co2All
import com.swu.ogg.database.DateSet


// DB나 서버 통신으로 받아온 데이터 관리하는 곳
// 데이터 변경이 감지되는 대로 UI 컴포넌트의 바인딩된 뷰에 나타냄
// 서버에서 얻은 데이터는 내부 데이터베이스에 저장하여 불러옴
class EnvViewModel : ViewModel() {

    private val _today = MutableLiveData<Int>().apply {
        value = DateSet.getDateToday()
    }
    val today: LiveData<Int> = _today

    // ───────────────────────────── 스탬프 ─────────────────────────────

    private var _list2 = MutableLiveData<ArrayList<StampItem>>().apply {
        value = arrayListOf<StampItem>()
    }
    var stamplist : LiveData<ArrayList<StampItem>> = _list2

    fun update() {
        _today.value = _today.value?.plus(1)
    }
}