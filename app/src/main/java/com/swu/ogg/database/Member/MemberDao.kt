package com.swu.ogg.database.Member

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

    @Query("SELECT * FROM memberTBL ORDER BY record ASC")
    fun getAlphabetizedMember() : Flow<List<MemberTBL>>
}