package com.swu.ogg.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// 유저활동 테이블 : 인덱스(1~21) | 등록활동(1~5)
@Entity(tableName = "user_project")
data class UserProject (@PrimaryKey(autoGenerate = true) val uIndex : Int,

                        @ColumnInfo var project1 : Int?,
                        @ColumnInfo var project2 : Int?,
                        @ColumnInfo var project3 : Int?,
                        @ColumnInfo var project4 : Int?,
                        @ColumnInfo var project5 : Int?
                        )

// 활동등록 테이블 : 인덱스 | 프로젝트차수, 일차, 활동명, 오늘누적탄소, 전체누적탄소, 같은활동포스트누적
@Entity(tableName = "post_project")
data class PostProject(@PrimaryKey(autoGenerate = true) val pIndex : Int,

                       @ColumnInfo val project_number : Int,
                       @ColumnInfo val Day : Int,
                       @ColumnInfo val activityTitle : String,
                       @ColumnInfo val SumTodayCo2 : Float,
                       @ColumnInfo val SumAllCo2 : Float,
                       @ColumnInfo val SumPost : Int
                       )
