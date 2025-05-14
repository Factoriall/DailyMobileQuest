package com.example.dailymobilequest.domain.repository

import com.example.dailymobilequest.data.AppDataWithQuestList
import com.example.dailymobilequest.data.AppDataDao
import javax.inject.Inject

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 */
class AppDataRepository @Inject constructor(
    private val appDataDao: AppDataDao
) {
    suspend fun insertAppData(appData: AppDataWithQuestList) {
        appDataDao.insert(appData)
    }
}