package com.example.dailymobilequest.data

/**
 * 알람 관련 데이터 클래스
 */
sealed class Alarm {
    data object No : Alarm()
    sealed class Yes(open val time: String) : Alarm() {
        data class Notification(override val time: String) : Yes(time)
        data class Sound(override val time: String) : Yes(time)

        override fun toString(): String {
            return when (this) {
                is Notification -> "노티 $time"
                is Sound -> "알람 $time"
            }
        }
    }

    override fun toString(): String {
        return when (this) {
            is No -> "알람 없음"
            is Yes -> this.toString()
        }
    }
}