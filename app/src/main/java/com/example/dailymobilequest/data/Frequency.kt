package com.example.dailymobilequest.data

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * 빈도 수 관련 데이터 클래스
 */
sealed class Frequency {
    data class Daily(
        val perDay: Int,
    ) : Frequency() {
        override fun toString(): String {
            return "${perDay}일 마다"
        }
    }

    data class Weekly(
        val dayOfWeek: List<DayOfWeek>,
    ) : Frequency() {
        override fun toString(): String {
            return "${dayOfWeek.joinToString(",") { it.text }}요일마다"
        }
    }

    data class FlexibleWeekly(
        val perWeek: Int,
        val startDayOfWeek: DayOfWeek,
        val dayOfWeeks: List<Int>,
    ) : Frequency() {
        init {
            require(perWeek > 1) {
                "perWeek must be greater than 1"
            }
        }

        override fun toString(): String {
            return "${startDayOfWeek.text}요일 시작/${perWeek}주 중 ${dayOfWeeks.joinToString(",")}일"
        }
    }

    data class Monthly(
        val dayOfMonth: List<DayOfMonth>
    ) : Frequency() {
        override fun toString(): String {
            return "매월 ${dayOfMonth.joinToString(",")}일"
        }
    }
}

enum class DayOfWeek(val text: String) {
    MON("월"), TUE("화"), WED("수"), THU("목"), FRI("금"), SAT("토"), SUN("일")
}

@JvmInline
value class DayOfMonth(
    val day: Int
) {
    init {
        require(day in 1..31)
    }

    override fun toString(): String {
        return "$day"
    }
}

class FrequencyTypeConverter {
    private val moshi: Moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(Frequency::class.java, "type")
                .withSubtype(Frequency.Daily::class.java, "daily")
                .withSubtype(Frequency.Weekly::class.java, "weekly")
                .withSubtype(Frequency.FlexibleWeekly::class.java, "flexibleWeekly")
                .withSubtype(Frequency.Monthly::class.java, "monthly")
        )
        .add(KotlinJsonAdapterFactory())
        .build()

    private val adapter = moshi.adapter(Frequency::class.java)

    @TypeConverter
    fun fromFrequency(frequency: Frequency?): String? = frequency?.let { adapter.toJson(it) }

    @TypeConverter
    fun toFrequency(json: String?): Frequency? = json?.let { adapter.fromJson(it) }
}
