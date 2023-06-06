package com.swu.ogg.database

import android.graphics.Bitmap
import androidx.room.*

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 활동 테이블 : 활동ID | 활동_제목, 활동_이미지, 활동_탄소, 활동_분류, 활동_빈도, 활동_제한, 활동_갤러리bool
@Entity(tableName = "activities")
data class ActivityTBL(@PrimaryKey(autoGenerate = true) val aID: Int,

                       @ColumnInfo val aCode: String,
                       @ColumnInfo val aTitle: String,
                       @ColumnInfo val aCo2: Float,
                       @ColumnInfo val aNum: Int,
                       @ColumnInfo val aFreq: Int,
                       @ColumnInfo val aLimit: Int?,
                       @ColumnInfo val aGallery: Boolean?,
                       @ColumnInfo val aImg: Bitmap? = null
)
// 설명 테이블 : 설명ID | 설명_디테일, 설명_이미지, 활동ID(foreign)
@Entity(tableName = "explains")
data class ExplainTBL(@PrimaryKey(autoGenerate = true) val eID: Int,

                     @ColumnInfo val eDetail: String?,
                     @ColumnInfo val eImg : Bitmap? = null,

                     @ColumnInfo val activityID: Int,
)

// 가이드 테이블 : 가이드ID | 가이드_이미지, 활동ID(foreign)
@Entity(tableName = "guidelines")
data class GuideTBL(@PrimaryKey(autoGenerate = true) var gID: Int,

                    @ColumnInfo val gGuide : String,
                    @ColumnInfo val gImg : Bitmap? = null,

                    @ColumnInfo val activityID: Int,
)

// 활동-설명 테이블 관계 정의
data class ActivityWithExplain(
    @Embedded val activity : ActivityTBL,
    @Relation(
        parentColumn = "aID",
        entityColumn = "activityID"
    )
    val explan : ExplainTBL
)

// 활동-가이드 테이블 관계 정의
data class ActivityWithGuide(
    @Embedded val activity: ActivityTBL,
    @Relation(
        parentColumn = "aID",
        entityColumn = "activityID"
    )
    val explan : GuideTBL
)

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 레벨 테이블 : 레벨ID | 레벨_이름, 레벨_탄소
@Entity(tableName = "levels")
data class LevelTBL(@PrimaryKey(autoGenerate = true) val lID: Int,
                    @ColumnInfo val lTitle : String,
                    @ColumnInfo val lAim : Float
)

// 멤버 테이블 : 멤버ID | 멤버_이름, 멤버_비밀번호, 멤버_자동차, 레벨_탄소(foreign)
@Entity(tableName = "members")
data class MemberTBL(@PrimaryKey(autoGenerate = true) val mID: Int,
                     @ColumnInfo val mName: String,
                     @ColumnInfo val mPw: String,
                     @ColumnInfo val mCar: Int?,

                     @ColumnInfo val levelAim : Float
)

// 레벨-멤버 테이블 관계 정의
data class LevelOfMembers(
    @Embedded val member :MemberTBL,
    @Relation(
        parentColumn = "lAim",
        entityColumn = "levelAim"
    )
    val level : LevelTBL
)

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 일회성 테이블 : 일회성ID | 일회성_제목, 일회성_이미지, 일회성_탄소, 일회성_분류, 일회성_가이드, 일회성_디테일
@Entity(tableName = "onlyActivities")
data class OnlyTBL(@PrimaryKey(autoGenerate = true) val oID: Int,
                   @ColumnInfo val oTitle: String,
                   @ColumnInfo val oCode: String,
                   @ColumnInfo val oGuide: String,
                   @ColumnInfo val oDetail: String?,
                   @ColumnInfo val oCo2: Float,
                   @ColumnInfo val oImg: Bitmap? = null
)

// 특별활동 테이블 : 특별활동ID | 특별활동_제목, 특별활동_이미지, 특별활동_탄소, 특별활동_가이드, 특별활동_디테일
@Entity(tableName = "specialActivities")
data class SpecialTBL(@PrimaryKey(autoGenerate = true) val sID: Int,

                      @ColumnInfo val sTitle: String,
                      @ColumnInfo val sGuide: String,
                      @ColumnInfo val sDetail: String?,
                      @ColumnInfo val sCo2: Float?,
                      @ColumnInfo val sImg: Bitmap? = null

)

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 배지 테이블 : 배지ID | 배지_이름, 배지_분류, 배지_획득조건, 배지_이미지
@Entity(tableName = "badges")
data class BadgeTBL(@PrimaryKey(autoGenerate = true) val bID: Int,

                    @ColumnInfo(name = "bCode") val bCode: String,
                    @ColumnInfo(name = "bTitle") val bTitle: String,
                    @ColumnInfo(name = "bRequire") val bRequire: String,
                    @ColumnInfo(name = "bImg") val bImg: Bitmap? = null
)

// 스티커 테이블 : 스티커ID | 스티커_이름, 스티커_설명, 스티커_이미지
@Entity(tableName = "stickers")
data class StickerTBL(@PrimaryKey(autoGenerate = true) val stID: Int,

                      @ColumnInfo val stTitle: String,
                      @ColumnInfo val stDetail: String?,
                      @ColumnInfo val stImg: Bitmap? = null,
)


