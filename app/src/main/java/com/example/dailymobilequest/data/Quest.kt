package com.example.dailymobilequest.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.Instant

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 */
@Entity
@TypeConverters(FrequencyTypeConverter::class, AlarmTypeConverter::class)
data class Quest(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val questName: String,
    val description: String,
    val startDate: Instant? = null,
    val endDate: Instant? = null,
    val frequency: Frequency,
    val alarm: Alarm
)
