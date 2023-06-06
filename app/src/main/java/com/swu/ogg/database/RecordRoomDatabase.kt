package com.swu.ogg.database

import android.content.Context
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
@Database(entities = [Record::class], version = 2, exportSchema = true)
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


//----------------Activity-------------------------------------------------------------
@Database(entities = [ActivityTBL::class], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConverter::class) //이미지 사용하는 경우에 필요
abstract class ActivityDatabase : RoomDatabase() {

    abstract fun activityDao(): ActivityDao

    companion object {
        @Volatile
        private var INSTANCE: ActivityDatabase? = null

        fun getDatabase(context: Context): ActivityDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this)  {
                val instance = Room.databaseBuilder(context.applicationContext, ActivityDatabase::class.java, "oggDB.db")
                    .createFromAsset("oggDB.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
//----------------Badge-------------------------------------------------------------
@Database(entities = [BadgeTBL::class], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConverter::class) //이미지 사용하는 경우에 필요
abstract class BadgeDatabase : RoomDatabase() {

    abstract fun badgeDao(): BadgeDao

    companion object {
        @Volatile
        private var INSTANCE: BadgeDatabase? = null

        fun getDatabase(context: Context): BadgeDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this)  {
                val instance = Room.databaseBuilder(context.applicationContext, BadgeDatabase::class.java, "oggDB.db")
                    .createFromAsset("oggDB.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


//----------------explane-------------------------------------------------------------
@Database(entities = [ExplanTBL::class], version = 1, exportSchema = true)
@TypeConverters(RoomTypeConverter::class) //이미지 사용하는 경우에 필요
abstract class ExplanDatabase : RoomDatabase() {

    abstract fun explanDao(): ExplanDao

    companion object {
        @Volatile
        private var INSTANCE: ExplanDatabase? = null

        fun getDatabase(context: Context): ExplanDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this)  {
                val instance = Room.databaseBuilder(context.applicationContext, ExplanDatabase::class.java, "oggDB.db")
                    .createFromAsset("oggDB.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


//----------------guide-------------------------------------------------------------
@Database(entities = [GuideTBL::class], version = 1, exportSchema = true)
@TypeConverters(RoomTypeConverter::class) //이미지 사용하는 경우에 필요
abstract class GuideDatabase : RoomDatabase() {

    abstract fun guideDao(): GuideDao

    companion object {
        @Volatile
        private var INSTANCE: GuideDatabase? = null

        fun getDatabase(context: Context): GuideDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this)  {
                val instance = Room.databaseBuilder(context.applicationContext, GuideDatabase::class.java, "oggDB.db")
                    .createFromAsset("oggDB.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


//----------------level-------------------------------------------------------------
@Database(entities = [LevelTBL::class], version = 1, exportSchema = true)
@TypeConverters(RoomTypeConverter::class) //이미지 사용하는 경우에 필요
abstract class LevelDatabase : RoomDatabase() {

    abstract fun levelDao(): LevelDao

    companion object {
        @Volatile
        private var INSTANCE: LevelDatabase? = null

        fun getDatabase(context: Context): LevelDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this)  {
                val instance = Room.databaseBuilder(context.applicationContext, LevelDatabase::class.java, "oggDB.db")
                    .createFromAsset("oggDB.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

//----------------member-------------------------------------------------------------
//@Database(entities = [MemberTBL::class], version = 1, exportSchema = true)
//@TypeConverters(RoomTypeConverter::class) //이미지 사용하는 경우에 필요
//abstract class MemberDatabase : RoomDatabase() {
//
//    abstract fun memberDao(): MemberDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: MemberDatabase? = null
//
//        fun getDatabase(context: Context): MemberDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) return tempInstance
//            synchronized(this)  {
//                val instance = Room.databaseBuilder(context.applicationContext, MemberDatabase::class.java, "oggDB.db")
//                    .createFromAsset("oggDB.db")
//                    .build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//    }
//}

//----------------oneoff-------------------------------------------------------------
@Database(entities = [OneoffTBL::class], version = 1, exportSchema = true)
@TypeConverters(RoomTypeConverter::class) //이미지 사용하는 경우에 필요
abstract class OneoffDatabase : RoomDatabase() {

    abstract fun oneoffDao(): OneoffDao

    companion object {
        @Volatile
        private var INSTANCE: OneoffDatabase? = null

        fun getDatabase(context: Context): OneoffDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this)  {
                val instance = Room.databaseBuilder(context.applicationContext, OneoffDatabase::class.java, "oggDB.db")
                    .createFromAsset("oggDB.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

//----------------particul-------------------------------------------------------------
@Database(entities = [ParticulTBL::class], version = 1, exportSchema = true)
@TypeConverters(RoomTypeConverter::class) //이미지 사용하는 경우에 필요
abstract class ParticulDatabase : RoomDatabase() {

    abstract fun particulDao(): ParticulDao

    companion object {
        @Volatile
        private var INSTANCE: ParticulDatabase? = null

        fun getDatabase(context: Context): ParticulDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this)  {
                val instance = Room.databaseBuilder(context.applicationContext, ParticulDatabase::class.java, "oggDB.db")
                    .createFromAsset("oggDB.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

//----------------particul-------------------------------------------------------------
@Database(entities = [StickerTBL::class], version = 1, exportSchema = true)
@TypeConverters(RoomTypeConverter::class) //이미지 사용하는 경우에 필요
abstract class StickerDatabase : RoomDatabase() {

    abstract fun stickerDao(): StickerDao

    companion object {
        @Volatile
        private var INSTANCE: StickerDatabase? = null

        fun getDatabase(context: Context): StickerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this)  {
                val instance = Room.databaseBuilder(context.applicationContext, StickerDatabase::class.java, "oggDB.db")
                    .createFromAsset("oggDB.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
