package com.swu.ogg.ui.myactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


// https://developer.android.com/training/basics/fragments/communicating?hl=ko
// <Fragment-Fragment 통신>
// - 공유된 viewModel 객체를 만듦
// - 두 Fragment 를 포함 하고 있는 MainActivity 에서 ViewModel 에 엑세스
// - Fragment 가 ViewModel 내 데이터 Update
//  ㄴ LiveData 통해 데이터 노출 ▶ 그걸 관찰하고 있는 한 새로운 상태가 다른 Fragment로 푸시
class MyActivityViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "프로젝트 시작 전"
    }
    val text: LiveData<String> = _text

}