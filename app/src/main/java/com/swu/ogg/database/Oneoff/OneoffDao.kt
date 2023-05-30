package com.swu.ogg.database.Oneoff

import androidx.room.*
import com.swu.ogg.database.OneoffTBL


@Dao
interface OneoffDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOneoff(vararg oID: OneoffTBL)

    @Update
    fun updateOneoff(vararg oID: OneoffTBL)

    @Delete
    fun deleteOneoff(oID: OneoffTBL)

    @Query("SELECT * FROM OneoffTBL")
    fun getAllMember(): List<OneoffTBL>
}
