package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.MemberTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(vararg mID: MemberTBL)

    @Update
    fun updateMember(vararg mID: MemberTBL)

    @Delete
    fun deleteMember(mID: MemberTBL)

    @Query("SELECT * FROM memberTBL")
    fun getAllMember() : List<MemberTBL>

    @Query("SELECT * FROM memberTBL ORDER BY mID ASC")
    fun getAlphabetizedMember() : Flow<List<MemberTBL>>

    // ────────────────────────────────────────────────── 외래키
    @Transaction
    @Query("SELECT * FROM MemberTBL")
    fun getLevelOfMembers() : Float
}