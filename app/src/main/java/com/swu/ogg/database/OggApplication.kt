package com.swu.ogg.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class OggApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // 앱 시작 시가 아니라 처음으로 필요해질 때 만들어야 하므로 by lazy.(코틀린 속성 위임) 사용
    // DB 인스턴스, DAO에 기반한 저장소 인스턴스 생성

    val database by lazy { OggRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { OggRepository(database.userDao()) }
}