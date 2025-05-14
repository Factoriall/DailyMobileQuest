package com.example.dailymobilequest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = AppData::class,
        parentColumns = ["app_id"],
        childColumns = ["quest_owner_id"],
        onDelete = CASCADE
    )],
    indices = [Index("quest_id")]
)
@TypeConverters(FrequencyTypeConverter::class)
data class Quest(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "quest_id") val id: Int,
    @ColumnInfo(name = "quest_owner_id") val questOwnerId: Long,
    @ColumnInfo(name = "quest_name") val questName: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "frequency") val frequency: Frequency
)
