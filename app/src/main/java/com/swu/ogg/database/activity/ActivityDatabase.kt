package com.swu.ogg.database.activity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.swu.ogg.database.ActivityTBL
import com.swu.ogg.database.RoomTypeConverter

@Database(entities = [ActivityTBL::class], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConverter::class) //이미지 사용하는 경우에 필요
public abstract class ActivityDatabase : RoomDatabase() {

    abstract fun activityDao(): ActivityDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ActivityDatabase? = null

        fun getDatabase(context: Context): ActivityDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ActivityDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}