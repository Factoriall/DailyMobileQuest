package com.example.dailymobilequest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * 퀘스트 데이터베이스 접근 객체
 */
@Dao
interface QuestDao {
    @Query("SELECT * FROM quest")
    fun getAll(): List<Quest>

    @Insert
    fun insertAll(vararg quest: Quest)
}