package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.ActivityTBL
import com.swu.ogg.database.ActivityWithExplan
import com.swu.ogg.database.ActivityWithGuide
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

    @Query("SELECT aTitle FROM activityTBL WHERE aID = :id")
    fun getActivityWithID(id: Int): String

    // ────────────────────────────────────────────────── 외래키
    @Transaction
    @Query("SELECT * FROM ActivityTBL")
    fun getActivitiesWithExplans() : Int

    @Transaction
    @Query("SELECT * FROM ActivityTBL")
    fun getActivitiesWithGuides() : Int
}