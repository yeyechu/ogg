package com.swu.ogg.database

import androidx.annotation.WorkerThread
import com.swu.ogg.database.dao.ActivityDao
import com.swu.ogg.database.dao.BadgeDao
import com.swu.ogg.database.dao.ExplainDao
import kotlinx.coroutines.flow.Flow

// 쿼리를 관리하고 여러 백엔드를 사용하도록 허용
// 데이터를 가져올 방법을 결정하는 로직을 구현하는 곳
// 데이터를 가져오는 방법 : (1) 네트워크 (2) 로컬 DB (3) 캐시

class RecordRepository(private val recordDao: RecordDao) {

    // Room은 분리된 스레드에서 모든 query를 실행
    // 데이터 변경이 있으면, Observed Flow가 observer에 알려줌

    val allRecords : Flow<List<Record>> = recordDao.getAlphabetizedRecords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(record : Record) {
        recordDao.insert(record)
    }
}
