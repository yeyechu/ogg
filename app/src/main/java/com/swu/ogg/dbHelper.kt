package com.swu.ogg

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.renderscript.ScriptGroup.Input
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

// oggDB.db를 위한 임시 사용

class dbHelper(context : Context) : SQLiteOpenHelper(context, FILE_NAME, null, 1){

    private val sqlite : SQLiteDatabase? = null
    private val oggContext : Context

    val filePath : String = "/data/data/" + context.packageName + "/databases/"

    init{
        oggContext = context
        checkDB()
        Log.d(TAG, "init")
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "onCreate()")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.disableWriteAheadLogging()
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        Log.d(TAG, "onOpen()")
    }

    // ─────────────────────────── db파일 존재 확인 ───────────────────────────
    private fun checkDB() {
        Log.d(TAG, "checkDB()")
        val dbFile = File(filePath + FILE_NAME)

        if(!dbFile.exists()){
            dbCopy()
            Log.d(TAG, "dbCopy()")
        }
    }
    // ─────────────────────────── 기존 DB 불러오기 ───────────────────────────
    private fun dbCopy() {

        try{
            val folder = File(filePath)

            if(!folder.exists()){
                folder.mkdir()
            }

            val myFile = filePath + FILE_NAME
            val iStream : InputStream = oggContext.assets.open(FILE_NAME)
            val oStream : OutputStream = FileOutputStream(myFile)

            var fileLength : Int
            fileLength = iStream.available()

            val buffer = ByteArray(fileLength)

            while(iStream.read(buffer).also { fileLength = it } > 0) {

                oStream.write(buffer, 0, fileLength)
            }

            oStream.flush()
            oStream.close()
            iStream.close()

        } catch (e : IOException) {
            e.printStackTrace()
            Log.d(TAG, "dbCopy(), 예외 발생")
        }
    }
    companion object {
        private const val TAG = "DBHelper" // Logcat에 출력할 태그이름
        private const val FILE_NAME = "oggDB.db"
    }

    override fun close() {
        Log.d(TAG, "close()")
        sqlite?.close()
        super.close()
    }
}