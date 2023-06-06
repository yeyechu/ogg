package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.ExplainTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface ExplainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExplan(vararg eID: ExplainTBL)

    @Update
    fun updateExplan(vararg eID: ExplainTBL)

    @Delete
    fun deleteExplan(eID: ExplainTBL)

    @Query("SELECT * FROM explainTBL")
    fun getAllExplan() : List<ExplainTBL>

    @Query("SELECT * FROM explainTBL ORDER BY eID ASC")
    fun getAllByIds(): Flow<List<ExplainTBL>>


}