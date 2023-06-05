package com.swu.ogg.database

import android.graphics.Bitmap
import androidx.room.*

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 활동 테이블 : 활동ID | 활동_제목, 활동_이미지, 활동_탄소, 활동_분류, 활동_빈도, 활동_제한, 활동_갤러리bool
@Entity(tableName = "activityTBL")
data class ActivityTBL(@PrimaryKey(autoGenerate = true) val aID: Int,

                       @ColumnInfo val aTitle: String,
                       @ColumnInfo val aImg: Bitmap? = null,
                       @ColumnInfo val aCo2: Float,
                       @ColumnInfo val aCode: Int,
                       @ColumnInfo val aNum: Int,
                       @ColumnInfo val aFreq: Int,
                       @ColumnInfo val aLimit: Int?,
                       @ColumnInfo val aGallery: Boolean?
)
// 설명 테이블 : 설명ID | 설명_디테일, 설명_이미지, 활동ID(foreign)
@Entity(tableName = "explanTBL")
data class ExplanTBL(@PrimaryKey(autoGenerate = true) var eID: Int,
                     @ColumnInfo val eDetail: String?,
                     @ColumnInfo val eImg : Bitmap? = null,

                     @ColumnInfo val aOwnerID: String,
)

// 가이드 테이블 : 가이드ID | 가이드_이미지, 활동ID(foreign)
@Entity(tableName = "guideTBL")
data class GuideTBL(@PrimaryKey(autoGenerate = true) var gID: Int,
                    @ColumnInfo val gGallery: String,
                    @ColumnInfo val gImg: Bitmap? = null,

                    @ColumnInfo val aOwnerID : Int
)

// 활동-설명 테이블 관계 정의
data class ActivityWithExplan(
    @Embedded val activity : ActivityTBL,
    @Relation(
        parentColumn = "aID",
        entityColumn = "aOwnerID"
    )
    val explan : ExplanTBL
)

// 활동-가이드 테이블 관계 정의
data class ActivityWithGuide(
    @Embedded val activity: ActivityTBL,
    @Relation(
        parentColumn = "aID",
        entityColumn = "aOwnerID"
    )
    val explan : ExplanTBL
)

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 레벨 테이블 : 레벨ID | 레벨_이름, 레벨_탄소
@Entity(tableName = "levelTBL")
data class LevelTBL(@PrimaryKey(autoGenerate = true) val lID: Int,
                    @ColumnInfo val lName: String,
                    @ColumnInfo val lDigit: Float
)

// 멤버 테이블 : 멤버ID | 멤버_이름, 멤버_비밀번호, 멤버_자동차, 레벨_탄소(foreign)
@Entity(tableName = "memberTBL")
data class MemberTBL(@PrimaryKey(autoGenerate = true) val mID: Int,
                     @ColumnInfo val mName: String,
                     @ColumnInfo val mPw: String,
                     @ColumnInfo val mCar: Int?,

                     @ColumnInfo val lOwnerDigit: Float
)

// 레벨-멤버 테이블 관계 정의
data class LevelOfMembers(
    @Embedded val member :MemberTBL,
    @Relation(
        parentColumn = "lDigit",
        entityColumn = "lOwnerDigit"
    )
    val level : LevelTBL
)

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 일회성 테이블 : 일회성ID | 일회성_제목, 일회성_이미지, 일회성_탄소, 일회성_분류, 일회성_가이드, 일회성_디테일
@Entity(tableName = "oneoffTBL")
data class OneoffTBL(@PrimaryKey(autoGenerate = true) val oID: Int,
                     @ColumnInfo val oTitle: String,
                     @ColumnInfo val oImg: Bitmap? = null,
                     @ColumnInfo val oCo2: Float,
                     @ColumnInfo val oCode: String,
                     @ColumnInfo val oGuide: String?,
                     @ColumnInfo val oDetail: String?
)

// 특별활동 테이블 : 특별활동ID | 특별활동_제목, 특별활동_이미지, 특별활동_탄소, 특별활동_가이드, 특별활동_디테일
@Entity(tableName = "particulTBL")
data class ParticulTBL(@PrimaryKey(autoGenerate = true) val pID: Int,
                  @ColumnInfo(name = "pTitle") val pTitle: String,
                  @ColumnInfo(name = "pImg") val pImg: Bitmap? = null,
                  @ColumnInfo(name = "pCo2") val pCo2: Float?,
                  @ColumnInfo(name = "pGuide") val pGuide: String?,
                  @ColumnInfo(name = "pDetail") val pDetail: String?
)

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 배지 테이블 : 배지ID | 배지_이름, 배지_분류, 배지_획득조건, 배지_이미지
@Entity(tableName = "badgeTBL")
data class BadgeTBL(@PrimaryKey(autoGenerate = true) var bID: Int,
                    @ColumnInfo(name = "bCode") var bCode: String?,
                    @ColumnInfo(name = "bTitle") var bTitle: String,
                    @ColumnInfo(name = "bRequire") var bRequire: String?,
                    @ColumnInfo(name = "bImg") var bImg: Bitmap? = null,
)

// 스티커 테이블 : 스티커ID | 스티커_이름, 스티커_설명, 스티커_이미지
@Entity(tableName = "stickerTBL")
data class StickerTBL(@PrimaryKey(autoGenerate = true) var sID: Int,
                 @ColumnInfo(name = "sTitle") var sTitle: String,
                 @ColumnInfo(name = "sDetail") var pDetail: String?,
                 @ColumnInfo(name = "sImg") var pImg: Bitmap? = null,
)


