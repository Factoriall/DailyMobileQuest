package com.example.dailymobilequest.data

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
        val perWeek: Int,
        val dayOfWeek: List<DayOfWeek>,
    ) : Frequency() {
        override fun toString(): String {
            return "${perWeek}주마다 ${dayOfWeek.joinToString(",") { it.text }}요일"
        }
    }

    data class Monthly(
        val dayOfMonth: List<DayOfMonth>
    ) : Frequency() {
        override fun toString(): String {
            return "${dayOfMonth.joinToString(",")}일"
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
