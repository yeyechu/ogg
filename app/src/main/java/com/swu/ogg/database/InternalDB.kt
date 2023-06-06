package com.swu.ogg.database

import android.graphics.Bitmap
import androidx.room.*

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 활동 테이블 : 활동ID | 활동분류, 제목, 탄소감축량, 분류번호, 빈도, 횟수제한, 앨범업로드여부, 이미지
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
// 설명 테이블 : 설명ID | 디테일, 이미지, 활동ID(foreign)
@Entity(tableName = "explains",
    foreignKeys = [
        ForeignKey(
            entity = ActivityTBL::class,
            parentColumns = ["aID"],
            childColumns = ["activityID"])])
data class ExplainTBL(@PrimaryKey(autoGenerate = true) val eID: Int,

                     @ColumnInfo val eDetail: String?,
                     @ColumnInfo val eImg : Bitmap? = null,

                     @ColumnInfo val activityID: Int,
)

// 가이드 테이블 : 가이드ID | 설명, 이미지, 활동ID(foreign)
@Entity(tableName = "guidelines",
    foreignKeys = [
        ForeignKey(
            entity = ActivityTBL::class ,
            parentColumns = ["aID"],
            childColumns = ["activityID"])])
data class GuideTBL(@PrimaryKey(autoGenerate = true) var gID: Int,

                    @ColumnInfo val gGuide : String,
                    @ColumnInfo val gImg : Bitmap? = null,

                    @ColumnInfo val activityID: Int,
)

// 활동-설명 테이블 관계 정의 : 외래키/일대다
data class ActivitiesWithExplains(
    @Embedded val activity : ActivityTBL,
    @Relation(
        parentColumn = "aID",
        entityColumn = "activityID"
    )
    val explain : List<ExplainTBL>?
)

// 활동-가이드 테이블 관계 정의 : 외래키/일대일
data class ActivitiesWithGuides(
    @Embedded val activity: ActivityTBL,
    @Relation(
        parentColumn = "aID",
        entityColumn = "activityID"
    )
    val explain : GuideTBL
)

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 레벨 테이블 : 레벨ID | 목표레벨명, 목표탄소
@Entity(tableName = "levels", indices = [Index(value = ["lAim"], unique = true)])
data class LevelTBL(@PrimaryKey(autoGenerate = true) val lID: Int,
                    @ColumnInfo val lTitle : String,
                    @ColumnInfo val lAim : Float
)

// 멤버 테이블 : 멤버ID | 사용자ID, 사용자PW, 자동차소유형태, 프로젝트횟수, 목표탄소(foreign)
@Entity(tableName = "members",
    foreignKeys = [
        ForeignKey(
            entity = LevelTBL::class,
            parentColumns = ["lAim"],
            childColumns = ["levelAim"])])
data class MemberTBL(@PrimaryKey(autoGenerate = true) val mID: Int,
                     @ColumnInfo val mName: String,
                     @ColumnInfo var mPw: String,
                     @ColumnInfo var mCar: Int?,
                     @ColumnInfo var mProject : Int = 0,

                     @ColumnInfo var levelAim : Float?
)

// 레벨-멤버 테이블 관계 정의 : 외래키, 일대다
data class LevelOfMembers(
    @Embedded val level : LevelTBL,
    @Relation(
        parentColumn = "lAim",
        entityColumn = "levelAim"
    )
    val member : List<MemberTBL>?
)

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 일회성 테이블 : 일회성ID | 제목, 활동분류, 활동가이드, 디테일, 탄소, 이미지
@Entity(tableName = "onlyActivities")
data class OnlyTBL(@PrimaryKey(autoGenerate = true) val oID: Int,
                   @ColumnInfo val oTitle: String,
                   @ColumnInfo val oCode: String,
                   @ColumnInfo val oGuide: String,
                   @ColumnInfo val oDetail: String?,
                   @ColumnInfo val oCo2: Float,
                   @ColumnInfo val oImg: Bitmap? = null
)

// 특별활동 테이블 : 특별활동ID | 제목, 활동가이드, 디테일, 탄소, 이미지
@Entity(tableName = "specialActivities")
data class SpecialTBL(@PrimaryKey(autoGenerate = true) val sID: Int,

                      @ColumnInfo val sTitle: String,
                      @ColumnInfo val sGuide: String,
                      @ColumnInfo val sDetail: String?,
                      @ColumnInfo val sCo2: Float?,
                      @ColumnInfo val sImg: Bitmap? = null

)

// ────────────────────────────────────────────────────────────────────────────────────────────────
// 배지 테이블 : 배지ID | 배지이름, 분류, 획득조건, 이미지
@Entity(tableName = "badges")
data class BadgeTBL(@PrimaryKey(autoGenerate = true) val bID: Int,

                    @ColumnInfo(name = "bCode") val bCode: String,
                    @ColumnInfo(name = "bTitle") val bTitle: String,
                    @ColumnInfo(name = "bRequire") val bRequire: String,
                    @ColumnInfo(name = "bImg") val bImg: Bitmap? = null
)

// 스티커 테이블 : 스티커ID | 스티커이름, 설명, 이미지
@Entity(tableName = "stickers")
data class StickerTBL(@PrimaryKey(autoGenerate = true) val stID: Int,

                      @ColumnInfo val stTitle: String,
                      @ColumnInfo val stDetail: String?,
                      @ColumnInfo val stImg: Bitmap? = null,
)


