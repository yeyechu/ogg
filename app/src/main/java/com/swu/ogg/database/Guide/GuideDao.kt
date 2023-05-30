package com.swu.ogg.database.Guide

import androidx.room.*
import com.swu.ogg.database.GuideTBL

@Dao
interface GuideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGuide(vararg gID: GuideTBL)

    @Update
    fun updateGuide(vararg gID: GuideTBL)

    @Delete
    fun deleteGuide(gID: GuideTBL)

    @Query("SELECT * FROM GuideTBL")
    fun getAllGuide() : List<GuideTBL>
}