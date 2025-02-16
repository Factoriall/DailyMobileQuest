package com.example.dailymobilequest.data

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