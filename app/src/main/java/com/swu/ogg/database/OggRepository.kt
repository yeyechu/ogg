package com.swu.ogg.database

import androidx.annotation.WorkerThread
import com.swu.ogg.database.dao.UserProjectDao
import kotlinx.coroutines.flow.Flow

//class OggRepository(private val userDao : UserProjectDao) {
//
//    val projectList : Flow<List<UserProject>> = userDao.getAllActivityList()
//
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun insert(project : UserProject){
//        userDao.insert(project)
//    }
//}