package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.StickerTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface StickerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSticker(vararg stickers : StickerTBL)

    @Update
    suspend fun updateSticker(vararg stickers : StickerTBL)

    @Delete
    suspend fun deleteSticker(stickers : StickerTBL)

    @Query("SELECT * FROM stickers")
    fun getAllSticker(): Flow<List<StickerTBL>>
}