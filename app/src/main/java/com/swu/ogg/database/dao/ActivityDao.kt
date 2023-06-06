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

    @Query("SELECT * FROM ActivityTBL")
    fun getAllActivity() : List<ActivityTBL>

    @Query("SELECT * FROM ActivityTBL ORDER BY aCode ASC")
    fun getAlphabetizedActivity(): Flow<List<ActivityTBL>>

    @Query("SELECT * FROM ActivityTBL WHERE aID = :id")
    fun getActivityWithID(id: Int): String

    // ────────────────────────────────────────────────── 외래키
//    @Transaction
//    @Query("SELECT aID FROM ActivityTBL")
//    fun getActivitiesWithExplans() : Int
//
//    @Transaction
//    @Query("SELECT aID FROM ActivityTBL")
//    fun getActivitiesWithGuides() : Int
}