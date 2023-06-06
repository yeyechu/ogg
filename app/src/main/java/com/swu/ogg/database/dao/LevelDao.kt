package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.LevelTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface LevelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevel(vararg levels : LevelTBL)

    @Update
    fun updateLevel(vararg levels : LevelTBL)

    @Delete
    fun deleteLevel(levels : LevelTBL)

    @Query("SELECT * FROM levels")
    fun getAllLevel() : List<LevelTBL>

}