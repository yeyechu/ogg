package com.swu.ogg.database.level

import androidx.room.*
import com.swu.ogg.database.LevelTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface LevelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLevel(vararg lID: LevelTBL)

    @Update
    fun updateLevel(vararg lID: LevelTBL)

    @Delete
    fun deleteLevel(lID: LevelTBL)

    @Query("SELECT * FROM LevelTBL")
    fun getAllLevel() : List<LevelTBL>

    @Query("SELECT * FROM levelTBL ORDER BY lID ASC")
    fun getAlphabetizedLevel(): Flow<List<LevelTBL>>
}