package com.swu.ogg.database.badge

import androidx.room.*
import com.swu.ogg.database.BadgeTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface BadgeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBadge(vararg bID: BadgeTBL)

    @Update
    fun updateBadge(vararg bID: BadgeTBL)

    @Delete
    fun deleteBadge(bID: BadgeTBL)

    @Query("SELECT * FROM badgeTBL")
    fun getAllBadge() : List<BadgeTBL>

    @Query("SELECT * FROM badgeTBL ORDER BY bID ASC")
    fun getAlphabetizedBadge(): Flow<List<BadgeTBL>>
}