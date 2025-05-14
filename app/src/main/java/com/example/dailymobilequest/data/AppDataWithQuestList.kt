package com.example.dailymobilequest.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * 앱 데이터와 퀘스트 리스트를 함께 저장하는 데이터 클래스
 */
data class AppDataWithQuestList(
    @Embedded val appData: AppData,
    @Relation(
        parentColumn = "app_id",
        entityColumn = "quest_owner_id"
    )
    val questList: List<Quest>
)