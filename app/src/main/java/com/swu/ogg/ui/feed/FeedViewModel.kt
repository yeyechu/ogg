package com.swu.ogg.ui.env

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "피드 : 2학기 공개"
    }
    val text: LiveData<String> = _text
}