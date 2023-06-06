package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.OnlyTBL
import com.swu.ogg.database.SpecialTBL
import kotlinx.coroutines.flow.Flow


@Dao
interface OnlyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnlyActivities(vararg onlyActivities : OnlyTBL)

    @Update
    fun updateOnlyActivities(vararg onlyActivities : OnlyTBL)

    @Delete
    fun deleteOnlyActivities(onlyActivities : OnlyTBL)

    @Query("SELECT * FROM onlyActivities")
    fun getAllOnlyActivities(): Flow<List<OnlyTBL>>

    @Query("SELECT * FROM onlyActivities WHERE oID = :id")
    fun setPostCardByIds(id : Int) : List<OnlyTBL>
}
