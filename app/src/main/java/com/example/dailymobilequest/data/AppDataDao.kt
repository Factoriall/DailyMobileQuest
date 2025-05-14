package com.example.dailymobilequest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 */
@Dao
interface AppDataDao {
    @Transaction
    @Query("SELECT * FROM AppData WHERE app_id = :id")
    suspend fun get(id: Int): List<AppDataWithQuestList>

    @Transaction
    @Query("SELECT * FROM AppData")
    suspend fun getAll(): List<AppDataWithQuestList>

    @Transaction
    suspend fun insert(data: AppDataWithQuestList) {
        insertAppData(data.appData)
        insertQuests(data.questList)
    }

    @Insert
    suspend fun insertAppData(data: AppData)

    @Insert
    suspend fun insertQuests(quests: List<Quest>)
}