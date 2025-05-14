package com.example.dailymobilequest

import android.content.Context
import androidx.room.Room
import com.example.dailymobilequest.data.AppDataDao
import com.example.dailymobilequest.data.QuestDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt를 사용한 데이터베이스 모듈
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }

    @Provides
    fun provideAppDataWithQuestListDao(database: AppDatabase): AppDataDao {
        return database.appDataWithQuestListDao()
    }

    @Provides
    fun provideQuestDao(database: AppDatabase): QuestDao {
        return database.questDao()
    }
}