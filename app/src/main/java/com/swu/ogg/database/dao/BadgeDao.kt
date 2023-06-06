package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.BadgeTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface BadgeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadge(vararg badges : BadgeTBL)

    @Update
    fun updateBadge(vararg badges : BadgeTBL)

    @Delete
    fun deleteBadge(badges : BadgeTBL)

    @Query("SELECT * FROM badges")
    fun getAllBadge() : Flow<List<BadgeTBL>>

    @Query("SELECT * FROM badges ORDER BY bID ASC")
    fun getAlphabetizedBadge(): Flow<List<BadgeTBL>>

    @Query("SELECT * FROM badges WHERE bcode = :code")
    fun getBadgesByCode(code : String) : Flow<List<BadgeTBL>>
}