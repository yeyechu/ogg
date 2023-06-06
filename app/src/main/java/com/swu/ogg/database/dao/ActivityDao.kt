package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(vararg activities : ActivityTBL)

    @Update
    suspend fun updateActivity(vararg activities : ActivityTBL)

    @Delete
    suspend fun deleteActivity(activities : ActivityTBL)

    @Query("DELETE FROM activities")
    suspend fun deleteAll()

    @Query("SELECT * FROM activities")
    fun getAllActivity() : Flow<List<ActivityTBL>>

    @Query("SELECT * FROM activities WHERE aID = :id")
    fun setTodayCardByIds(id : Int) : List<ActivityTBL>
}

@Dao
interface ExplainDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExplain(vararg explains : ExplainTBL)

    @Update
    fun updateExplain(vararg explains : ExplainTBL)

    @Delete
    fun deleteExplain(explains : ExplainTBL)

    @Query("SELECT * FROM explains WHERE activityID = :activityID")
    fun getAllByIds(activityID : Int): List<ExplainTBL>

}
// ────────────────────────────────────────────────── 외래키
@Dao
interface ActivityExplainDao {

    @Transaction
    @Query("SELECT * FROM activities")
    suspend fun getAllActivitiesWithExplains() : List<ActivitiesWithExplains>?
    @Transaction
    @Query("SELECT * FROM activities WHERE aID = :activityID")
    fun getActivitiesWithExplains(activityID : Int) : ActivitiesWithExplains?
}

@Dao
interface GuideDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuide(vararg guidelines : GuideTBL)

    @Update
    fun updateGuide(vararg guidelines : GuideTBL)

    @Delete
    fun deleteGuide(guidelines : GuideTBL)

    @Query("SELECT * FROM guidelines")
    fun getAllGuide() : List<GuideTBL>

    @Query("SELECT * FROM guidelines ORDER BY gID ASC")
    fun getAlphabetizedGuide(): Flow<List<GuideTBL>>

    //────────────────────────────────────────────────── 외래키
//    @Transaction
//    @Query("SELECT * FROM guideslines")
//    fun getAllActivitiesWithGuides() : List<GuideTBL>
//    @Transaction
//    @Query("SELECT * FROM guidelines WHERE activityID = :id")
//    fun getActivitiesWithGuides(id : Int) : Int

}
@Dao
interface ActivityGuideDao {

    @Transaction
    @Query("SELECT * FROM activities")
    fun getAllActivitiesWithGuides() : List<ActivitiesWithGuides>
    @Transaction
    @Query("SELECT * FROM activities WHERE aID = :activityID")
    fun getActivitiesWithGuides(activityID : Int) : ActivitiesWithGuides

}