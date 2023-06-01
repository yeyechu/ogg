package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.ExplanTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface ExplanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExplan(vararg eID: ExplanTBL)

    @Update
    fun updateExplan(vararg eID: ExplanTBL)

    @Delete
    fun deleteExplan(eID: ExplanTBL)

    @Query("SELECT * FROM explanTBL")
    fun getAllExplan() : List<ExplanTBL>

    @Query("SELECT * FROM explanTBL ORDER BY eID ASC")
    fun getAlphabetizedExplan(): Flow<List<ExplanTBL>>
}