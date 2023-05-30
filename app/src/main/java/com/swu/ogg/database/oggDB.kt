package com.swu.ogg.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Blob


@Entity(tableName = "activityTBL")
class ActivityTBL(@PrimaryKey(autoGenerate = true) @ColumnInfo var aID: Int,
                  @ColumnInfo(name = "aTitle") var aTitle: String,
                  @ColumnInfo(name = "aImg") var aImg: Blob?,
                  @ColumnInfo(name = "aCo2") var aCo2: Float,
                  @ColumnInfo(name = "aCode") var aCode: Int,
                  @ColumnInfo(name = "aNum") var aNum: Int,
                  @ColumnInfo(name = "aFreq") var aFreq: Int,
                  @ColumnInfo(name = "aLimit") var aLimit: Int?,
                  @ColumnInfo(name = "aGallery") var aGallery: Boolean?
)

@Entity(tableName = "badgeTBL")
class BadgeTBL(@PrimaryKey(autoGenerate = true) var bID: Int,
               @ColumnInfo(name = "bCode") var bCode: String?,
               @ColumnInfo(name = "bTitle") var bTitle: String,
               @ColumnInfo(name = "bRequire") var bRequire: String?,
               @ColumnInfo(name = "bImg") var bImg: Blob?
)

@Entity(tableName = "explanTBL",  foreignKeys = [
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
class ExplanTBL(@PrimaryKey(autoGenerate = true) var eID: Int,
                @ColumnInfo(name = "eDetail") var eDetail: String?,
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
               @ColumnInfo(name = "gGuide") var gGallery: String,
               @ColumnInfo(name = "gImg") var gImg: Blob?
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

@Entity(tableName = "oneoffTBL")
class OneoffTBL(@PrimaryKey(autoGenerate = true) var oID: Int,
                @ColumnInfo(name = "oTitle") var oTitle: String,
                @ColumnInfo(name = "oImg") var oImg: Blob?,
                @ColumnInfo(name = "oCo2") var oCo2: Float,
                @ColumnInfo(name = "oCode") var oCode: String,
                @ColumnInfo(name = "oGuide") var oGuide: String?,
                @ColumnInfo(name = "oDetail") var oDetail: String?
)

@Entity(tableName = "particulTBL")
class ParticulTBL(@PrimaryKey(autoGenerate = true) var pID: Int,
                  @ColumnInfo(name = "pTitle") var pTitle: String,
                  @ColumnInfo(name = "pImg") var pImg: Blob?,
                  @ColumnInfo(name = "pCo2") var pCo2: Float?,
                  @ColumnInfo(name = "pGuide") var pGuide: String?,
                  @ColumnInfo(name = "pDetail") var pDetail: String?
)

@Entity(tableName = "stickerTBL")
class StickerTBL(@PrimaryKey(autoGenerate = true) var sID: Int,
                 @ColumnInfo(name = "sTitle") var sTitle: String,
                 @ColumnInfo(name = "sDetail") var pDetail: String?,
                 @ColumnInfo(name = "sImg") var pImg: Blob?
)
