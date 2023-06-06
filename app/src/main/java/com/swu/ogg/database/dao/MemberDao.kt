package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.MemberTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(vararg members : MemberTBL)

    @Update
    fun updateMember(vararg members : MemberTBL)

    @Delete
    fun deleteMember(members : MemberTBL)

    @Query("SELECT * FROM members")
    fun getAllMember() : Flow<List<MemberTBL>>

    @Query("SELECT * FROM members ORDER BY mID ASC")
    fun getAlphabetizedMember() : Flow<List<MemberTBL>>

}
// ────────────────────────────────────────────────── 외래키
@Dao
interface MemberLevelDao{

    @Transaction
    @Query("SELECT levelAim FROM members WHERE mID = :memberID")
    fun getLevelOfMembers(memberID : String) : Float

}