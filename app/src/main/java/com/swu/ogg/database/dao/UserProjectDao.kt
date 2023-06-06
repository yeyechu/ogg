package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.UserProject
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project : UserProject)

    @Update
    fun update(vararg project: UserProject)

    @Query("DELETE FROM user_project")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_project")
    fun getAllActivityList() : Flow<List<UserProject>>

    @Query("SELECT * FROM user_project WHERE uIndex = :index")
    fun getActivityList(index : Int) : List<UserProject>
}