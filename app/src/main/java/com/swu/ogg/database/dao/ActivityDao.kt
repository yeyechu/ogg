package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.ActivityTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActivity(vararg aID: ActivityTBL)

    @Update
    fun updateActivity(vararg aID: ActivityTBL)

    @Delete
    fun deleteActivity(aID: ActivityTBL)

    @Query("SELECT * FROM activityTBL")
    fun getAllActivity() : List<ActivityTBL>

    @Query("SELECT * FROM activityTBL ORDER BY aID ASC")
    fun getAlphabetizedActivity(): Flow<List<ActivityTBL>>
}