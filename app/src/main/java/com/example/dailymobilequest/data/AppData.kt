package com.example.dailymobilequest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 퀘스트 대상의 앱 클래스
 */
@Entity
data class AppData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "app_id") val id: Int = 0,
    @ColumnInfo(name = "app_name") val appName: String,
    @ColumnInfo(name = "package_name") val packageName: String,
    @ColumnInfo(name = "store_name") val storeName: String,
)