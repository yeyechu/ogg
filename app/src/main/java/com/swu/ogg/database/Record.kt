package com.swu.ogg.database

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

@Entity(tableName = "activityTBL")
class ActivityTBL(@PrimaryKey(autoGenerate = true) var aID: Int,
                  @ColumnInfo(name = "aTitle") var aTitle: String,
                  @ColumnInfo(name = "aImg") var aImg: Blob?,
                  @ColumnInfo(name = "aDetail1") var aDetail1: String?,
                  @ColumnInfo(name = "aDetail2") var aDetail2: String?,
                  @ColumnInfo(name = "aDetail3") var aDetail3: String?,
                  @ColumnInfo(name = "aDetail4") var aDetail4: String?
)

@Entity(tableName = "badgeTBL")
class BadgeTBL(@PrimaryKey(autoGenerate = true) var bID: Int,
                  @ColumnInfo(name = "bCode") var bCode: String,
                  @ColumnInfo(name = "bTitle") var bTitle: String?,
                  @ColumnInfo(name = "bImg") var bImg: Blob?
)

@Entity(tableName = "co2TBL",  foreignKeys = [
    ForeignKey(
        entity = ActivityTBL::class,
        parentColumns = ["aID"],
        childColumns = ["aID"]
    ),
    ForeignKey(
        entity = ActivityTBL::class,
        parentColumns = ["aTitle"],
        childColumns = ["aTitle"]
    )
])
class Co2TBL(@PrimaryKey(autoGenerate = true) var cID: Int,
             @ColumnInfo(name = "cCode") var cCode: String?,
             @ColumnInfo(name = "cNum") var cNum: String?,
             @ColumnInfo(name = "cReduce") var cReduce: String?,
             @ColumnInfo(name = "cFreq") var cFreq: String?,
             @ColumnInfo(name = "cLimit") var cLimit: String?
)

@Entity(tableName = "guideTBL",  foreignKeys = [
    ForeignKey(
        entity = ActivityTBL::class,
        parentColumns = ["aID"],
        childColumns = ["aID"]
    ),
    ForeignKey(
        entity = ActivityTBL::class,
        parentColumns = ["aTitle"],
        childColumns = ["aTitle"]
    )
])
class GuideTBL(@PrimaryKey(autoGenerate = true) var gID: Int,
             @ColumnInfo(name = "gGallery") var gGallery: String,
             @ColumnInfo(name = "gDetail") var gDetail: String?,
             @ColumnInfo(name = "gImg") var gImg: Blob
)

@Entity(tableName = "levelTBL")
class LevelTBL(@PrimaryKey(autoGenerate = true) var lID: Int,
               @ColumnInfo(name = "lName") var lName: String?,
               @ColumnInfo(name = "lDigit") var lDigit: Int?
)

@Entity(tableName = "memberTBL")
class MemberTBL(@PrimaryKey(autoGenerate = true) var mID: Int,
                @ColumnInfo(name = "mName") var mName: String,
                @ColumnInfo(name = "mPw") var mPw: String,
                @ColumnInfo(name = "lName") var lName: String?,
                @ColumnInfo(name = "mCar") var mCar: Int?
)

@Entity(tableName = "stickerTBL")
class StickerTBL(@PrimaryKey(autoGenerate = true) var sID: Int,
                @ColumnInfo(name = "sTitle") var sTitle: String,
                @ColumnInfo(name = "sDetail") var sDetail: String?,
                @ColumnInfo(name = "sImg") var sImg: Blob?,
)

