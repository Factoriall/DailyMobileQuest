package com.example.dailymobilequest

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dailymobilequest.data.Application
import com.example.dailymobilequest.data.ApplicationDao
import com.example.dailymobilequest.data.QuestDao

/**
 * 앱 데이터베이스 클래스
 */
@Database(entities = [Application::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questAppDao(): ApplicationDao
    abstract fun questDao(): QuestDao
}