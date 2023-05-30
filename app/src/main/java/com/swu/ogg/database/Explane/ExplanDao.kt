package com.swu.ogg.database.Explane

import androidx.room.*
import com.swu.ogg.database.ExplanTBL

@Dao
interface ExplanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExplan(vararg eID: ExplanTBL)

    @Update
    fun updateExplan(vararg eID: ExplanTBL)

    @Delete
    fun deleteExplan(eID: ExplanTBL)

    @Query("SELECT * FROM ExplanTBL")
    fun getAllExplan() : List<ExplanTBL>
}