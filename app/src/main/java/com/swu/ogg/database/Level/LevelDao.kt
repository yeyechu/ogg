package com.swu.ogg.database.Level

import androidx.room.*
import com.swu.ogg.database.LevelTBL

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
}