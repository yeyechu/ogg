package com.swu.ogg.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// 데이터 액세스 객체 DAO에서 SQL 쿼리를 지정하여 매서드 호출과 연결
// 컴파일러는 SQL을 확인하고 @Insert와 같은 일반 쿼리의 편의 주석으로 쿼리를 생성함
@Dao
interface RecordDao {

    // Flow 유형 반환 : 비동기 작업에서 한 번에 하나씩 생성, API 전체에 코루틴 지원
    @Query("SELECT * FROM record_table ORDER BY record ASC")
    fun getAlphabetizedRecords() : Flow<List<Record>>

    // 이미 존재하는 데이터라면 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record : Record)

    @Query("DELETE FROM record_table")
    suspend fun deleteAll()
}
