package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.ActivityTBL
import com.swu.ogg.database.SpecialTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecialDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecialActivities(vararg specialActivities : SpecialTBL)

    @Update
    fun updateSpecialActivities(vararg specialActivities : SpecialTBL)

    @Delete
    fun deleteSpecialActivities(specialActivities : SpecialTBL)

    @Query("SELECT * FROM specialActivities")
    fun getAllSpecialActivities(): Flow<List<SpecialTBL>>

    @Query("SELECT * FROM specialActivities WHERE sID = :id")
    fun setPostCardByIds(id : Int) : List<SpecialTBL>

}