package com.swu.ogg

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// oggDB.db를 위한 임시 사용

class dbHelper(context : Context?, name : String? ) : SQLiteOpenHelper(context, name, null, 1){

    override fun onCreate(db: SQLiteDatabase) {}

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.disableWriteAheadLogging()
    }
}