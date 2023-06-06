package com.swu.ogg.database.dao

import androidx.room.*
import com.swu.ogg.database.PostProject
import kotlinx.coroutines.flow.Flow

@Dao
interface PostProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project : PostProject)

    @Update
    fun update(vararg project: PostProject)

    @Delete
    suspend fun delete(project: PostProject)

    @Query("DELETE FROM post_project")
    suspend fun deleteAll()

//    @Query("DELETE * FROM post_project WHERE project_number = :num")
//    suspend fun deleteSet(num : Int)

    @Query("SELECT * FROM post_project")
    fun getAllPostList() : Flow<List<PostProject>>
}