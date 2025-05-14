package com.example.dailymobilequest

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dailymobilequest.data.AppData
import com.example.dailymobilequest.data.AppDataDao
import com.example.dailymobilequest.data.Quest
import com.example.dailymobilequest.data.QuestDao

/**
 * 앱 데이터베이스 클래스
 */
@Database(entities = [AppData::class, Quest::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDataWithQuestListDao(): AppDataDao
    abstract fun questDao(): QuestDao
}