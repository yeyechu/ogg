package com.swu.ogg.database

import android.content.Context
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.swu.ogg.database.dao.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// SQLiteOpenHelper에서 하던 처리를 담당
// Room Query가 Flow를 반환해주면 자동으로 백그라운드 스레드에서 비동기식 실행
// UI 성능 저하를 위해 필수적으로 Flow 필요
// 전체 앱에 RoomDatabase 인스턴스는 1개만 있어도 됨
// exportSchema : 데이터베이스 이전에 관한 변수, 버전 제어 시스템으로 확인할 수 있도록 스키마를 내보내는 디렉터리 설정 필요
@Database(entities = [
    ActivityTBL::class,
    GuideTBL::class,
    ExplainTBL::class,
    LevelTBL::class,
    BadgeTBL::class,
    OnlyTBL::class,
    SpecialTBL::class,
    StickerTBL::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
public abstract class OggRoomDatabase : RoomDatabase() {

    abstract fun activityDao(): ActivityDao
    abstract fun guideDao() : GuideDao
    abstract fun explainDao() : ExplainDao
    abstract fun activityGuideDao() : ActivityGuideDao
    abstract fun activityExplainDao() : ActivityExplainDao

    abstract fun onlyActivityDao() : OnlyDao
    abstract fun specialActivityDao() : SpecialDao

    abstract fun levelDao() : LevelDao
//    abstract fun memberDao() : MemberDao
//    abstract fun memberLevelDao() : MemberLevelDao

    abstract fun badgeDao() : BadgeDao
    abstract fun stickerDao() : StickerDao

//    abstract fun userDao() : UserProjectDao
//    abstract fun postDao() : PostProjectDao

    private class OggDatabaseCallback(
        private val scope : CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let{database ->
                scope.launch {


//                    // ───────────────────────────────────────────────────── Activity
//                    var activityDao = database.activityDao()
//                    activityDao.deleteAll()
//
//                    var cursorActivity = ActivityTBL(1, "C", "냉난방 온도 조절", 0.5f, 1001, 1, null, false, null)
//                    activityDao.insertActivity(cursorActivity)
//
//                    // ───────────────────────────────────────────────────── Guide
//                    var guideDao = database.guideDao()
//                    guideDao.getAllGuide()
//
//                    var cursorGuide = GuideTBL(1, " ", null, 1)
//                    guideDao.insertGuide(cursorGuide)
//
//                    // ─────────────────────────────────────────────────────
//
//
//                    // ───────────────────────────────────────────────────── UserProject
//                    var userProjectDao = database.userDao()
//
//                    userProjectDao.deleteAll()
//
//                    var cursorUserProject = UserProject(0, 1, 1, 1, 1, 1)
//                    userProjectDao.insert(cursorUserProject)
//                    cursorUserProject = UserProject(1, 1, 1, 1, 1, 1)
//                    userProjectDao.insert(cursorUserProject)
//                    cursorUserProject = UserProject(2, 1, 1, 1, 1, 1)
//                    userProjectDao.insert(cursorUserProject)
                }
            }
        }
    }

    // 여러 인스턴스가 동시에 열리는 것을 막기 위해 RoomDatabase를 Singleton로 정의
    companion object {

        @Volatile
        private var INSTANCE: OggRoomDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ) : OggRoomDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OggRoomDatabase::class.java,
                "ogg_database"
                )
                    .addCallback(OggDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}