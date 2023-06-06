package com.swu.ogg.database

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.swu.ogg.database.dao.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


// SQLiteOpenHelper에서 하던 처리를 담당
// Room Query가 Flow를 반환해주면 자동으로 백그라운드 스레드에서 비동기식 실행
// UI 성능 저하를 위해 필수적으로 Flow 필요
// 전체 앱에 RoomDatabase 인스턴스는 1개만 있어도 됨
// exportSchema : 데이터베이스 이전에 관한 변수, 버전 제어 시스템으로 확인할 수 있도록 스키마를 내보내는 디렉터리 설정 필요
@Database(entities = [Record::class], version = 1)
abstract class RecordRoomDatabase : RoomDatabase() {

    abstract fun recordDao() : RecordDao

    private class RecordDatabasesCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.recordDao())
                }
            }
        }

        suspend fun populateDatabase(recordDao : RecordDao) {

            recordDao.deleteAll()

            val record = Record("1")
            recordDao.insert(record)
        }


    }
    companion object {
        // 여러 인스턴스가 동시에 열리는 것을 막기 위해 RecordRoomDatabase를 Singleton로 정의
        @Volatile
        private var INSTANCE : RecordRoomDatabase? = null

        fun getDatabase(
            context : Context,
            scope : CoroutineScope
        ) : RecordRoomDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecordRoomDatabase::class.java,
                    "record_database"
                )
                    .addCallback(RecordDatabasesCallback(scope))
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
