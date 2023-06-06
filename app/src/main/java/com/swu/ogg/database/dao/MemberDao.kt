package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.LevelOfMembers
import com.swu.ogg.database.LevelTBL
import com.swu.ogg.database.MemberTBL
import kotlinx.coroutines.flow.Flow

@Dao
interface LevelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLevel(vararg levels : LevelTBL)

    @Update
    fun updateLevel(vararg levels : LevelTBL)

    @Delete
    fun deleteLevel(levels : LevelTBL)

    @Query("SELECT * FROM levels")
    fun getAllLevel() : List<LevelTBL>

}

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
    @Query("SELECT * from levels")
    suspend fun getAll() : List<LevelOfMembers>?
    @Transaction
    @Query("SELECT * FROM levels WHERE lAim = :levelAim")
    suspend fun getLevelOfMembers(levelAim : Float) : LevelOfMembers?

}