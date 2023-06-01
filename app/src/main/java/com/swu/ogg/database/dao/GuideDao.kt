package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.GuideTBL
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM guideTBL ORDER BY gID ASC")
    fun getAlphabetizedGuide(): Flow<List<GuideTBL>>
}