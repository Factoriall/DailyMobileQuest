package com.example.dailymobilequest.data

import android.graphics.drawable.Drawable

/**
 * 앱의 이름, 아이콘, 패키지 이름, 스토어 이름을 나타내는 데이터 클래스
 */
data class ApplicationProfile(
    val appName: String,
    val iconDrawable: Drawable,
    val packageName: String,
    val storeName: StoreName = StoreName()
)