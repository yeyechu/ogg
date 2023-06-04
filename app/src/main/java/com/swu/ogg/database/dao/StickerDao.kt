package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.StickerTBL

@Dao
interface StickerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSticker(vararg sID: StickerTBL)

    @Update
    fun updateSticker(vararg sID: StickerTBL)

    @Delete
    fun deleteSticker(sID: StickerTBL)

    @Query("SELECT * FROM StickerTBL")
    fun getAllParticul(): List<StickerTBL>
}