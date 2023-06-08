package com.swu.ogg.database

object DateSet {

    var date : Int = 0

    fun setDateToday(day : Int) {
        date = day
    }

    fun getDateToday() : Int {
        return date
    }

    fun resetDateToday() {
        date = 0
    }
}