package com.swu.ogg.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_table")
data class Record (
    @PrimaryKey
    @ColumnInfo(name = "record") val record: String
        )