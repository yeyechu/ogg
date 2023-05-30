package com.swu.ogg.database.Badge

import androidx.room.*
import com.swu.ogg.database.BadgeTBL

@Dao
interface BadgeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBadge(vararg bID: BadgeTBL)

    @Update
    fun updateBadge(vararg bID: BadgeTBL)

    @Delete
    fun deleteBadge(bID: BadgeTBL)

    @Query("SELECT * FROM BadgeTBL")
    fun getAllBadge() : List<BadgeTBL>
}