package com.swu.ogg.database.Particul

import androidx.room.*
import com.swu.ogg.database.ParticulTBL

interface ParticulDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertParticul(vararg oID: ParticulTBL)

    @Update
    fun updateParticul(vararg oID: ParticulTBL)

    @Delete
    fun deleteParticul(oID: ParticulTBL)

    @Query("SELECT * FROM ParticulTBL")
    fun getAllParticul(): List<ParticulTBL>
}