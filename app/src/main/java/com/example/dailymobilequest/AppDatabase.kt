package com.example.dailymobilequest

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dailymobilequest.data.QuestApp
import com.example.dailymobilequest.data.QuestAppDao

/**
 * 앱 데이터베이스 클래스
 */
@Database(entities = [QuestApp::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questAppDao(): QuestAppDao
}