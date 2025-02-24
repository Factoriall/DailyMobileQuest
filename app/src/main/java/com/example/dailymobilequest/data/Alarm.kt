package com.example.dailymobilequest.data

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory

/**
 * 알람 관련 데이터 클래스
 */
sealed class Alarm {
    data object No : Alarm() {
        override fun toString(): String {
            return "알람 없음"
        }
    }

    sealed class Yes(open val time: String) : Alarm() {
        data class Notification(override val time: String) : Yes(time) {
            override fun toString(): String {
                return "노티 $time"
            }
        }

        data class Sound(override val time: String) : Yes(time) {
            override fun toString(): String {
                return "알람 $time"
            }
        }
    }
}

class AlarmTypeConverter {
    private val moshi: Moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(Alarm::class.java, "type")
                .withSubtype(Alarm.No::class.java, "no")
                .withSubtype(Alarm.Yes.Sound::class.java, "sound")
                .withSubtype(Alarm.Yes.Notification::class.java, "notification")
        )
        .build()

    private val adapter = moshi.adapter(Alarm::class.java)

    @TypeConverter
    fun fromAlarm(alarm: Alarm?): String? = alarm?.let { adapter.toJson(it) }

    @TypeConverter
    fun toAlarm(json: String?): Alarm? = json?.let { adapter.fromJson(it) }
}