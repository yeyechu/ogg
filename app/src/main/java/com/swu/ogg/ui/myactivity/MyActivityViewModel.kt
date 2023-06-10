package com.swu.ogg.ui.myactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swu.ogg.database.Co2All
import com.swu.ogg.database.Co2Today

// https://developer.android.com/training/basics/fragments/communicating?hl=ko
// <Fragment-Fragment 통신>
// - 공유된 viewModel 객체를 만듦
// - 두 Fragment 를 포함 하고 있는 MainActivity 에서 ViewModel 에 엑세스
// - Fragment 가 ViewModel 내 데이터 Update
//  ㄴ LiveData 통해 데이터 노출 ▶ 그걸 관찰하고 있는 한 새로운 상태가 다른 Fragment로 푸시
class MyActivityViewModel : ViewModel() {

    // 이름 업데이트
    private val _text = MutableLiveData<String>().apply {
        value = "오지지"
    }
    val text: LiveData<String> = _text

    // 카드리스트 업데이트
    private var _list = MutableLiveData<ArrayList<CardItem>>().apply {
        value = arrayListOf<CardItem>()
    }

    var tolist : LiveData<ArrayList<CardItem>> = _list
    var onlist : LiveData<ArrayList<CardItem>> = _list

    // 오늘 게이지 업데이트
    private val _float = MutableLiveData<Float>().apply {
        value = Co2Today.getCo2Today()
    }
    val float : LiveData<Float> = _float

    fun increaseToday(result : Float) {
        _float.value = _float.value?.plus(result)
    }

    private var _progress = MutableLiveData<Int>().apply {
        value = ((Co2Today.getCo2Today() / 1.4f)*100).toInt()
    }
    val process : LiveData<Int> = _progress

    // 전체 게이지 업데이트
    private val _allday = MutableLiveData<Float>().apply {
        value = Co2All.getCo2All()
    }

    val allday: LiveData<Float> = _allday

    fun increaseAll() {
        _allday.value = _allday.value?.plus(Co2All.getCo2All())
    }

    fun processSet(result : Int){
        _progress.value = _progress.value?.plus(result)
    }

}
