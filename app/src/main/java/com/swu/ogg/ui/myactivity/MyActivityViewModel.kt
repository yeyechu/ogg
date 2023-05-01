package com.swu.ogg.ui.myactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyActivityViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "나의 활동(구현할 곳)"
    }
    val text: LiveData<String> = _text
}