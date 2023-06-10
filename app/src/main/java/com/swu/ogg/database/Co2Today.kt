package com.swu.ogg.database

object Co2Today {
    var co2 : Float = 0f

    fun setCo2Today(kg : Float) {
        co2 = kg
    }

    fun getCo2Today() : Float {
        return co2
    }
}