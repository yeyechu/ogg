package com.swu.ogg.database.Record

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "record_table")
data class Record (
    @PrimaryKey
    @ColumnInfo(name = "record") val record: String
)
