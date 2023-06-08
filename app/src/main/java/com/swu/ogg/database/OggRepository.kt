package com.swu.ogg.database

import androidx.annotation.WorkerThread
import com.swu.ogg.database.dao.UserProjectDao
import kotlinx.coroutines.flow.Flow

// 앱에서 사용하는 데이터와 통신하는 역할
// 뷰모델이 DB에 접근하지 않고 Repository에서 데이터 통신을 하게 함

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