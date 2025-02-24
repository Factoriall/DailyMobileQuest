package com.example.dailymobilequest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 */
@Dao
interface QuestDao {
    @Query("SELECT * FROM quest")
    fun getAll(): List<Quest>

    @Insert
    fun insertAll(vararg quest: Quest)
}