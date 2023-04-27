package com.swu.ogg.ui.indicators

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IndicatorsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "통계페이지"
    }
    val text: LiveData<String> = _text
}