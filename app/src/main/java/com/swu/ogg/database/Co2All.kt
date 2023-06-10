package com.swu.ogg.database

object Co2All {
    var co2 : Float = 0f

    fun setCo2All(kg : Float) {
        co2 = kg
    }

    fun getCo2All() : Float {
        return co2
    }
}