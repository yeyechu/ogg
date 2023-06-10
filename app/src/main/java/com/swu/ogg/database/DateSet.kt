package com.swu.ogg.database

// 날짜가 바뀌면 여기에 저장됨
object DateSet {

    var date : Int = 0

    fun setDateToday(day : Int) {
        date = day
    }

    fun getDateToday() : Int {
        return date
    }
}