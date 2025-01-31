package com.example.dailymobilequest.data

/**
 * 스토어 이름 클래스
 */
@JvmInline
value class StoreName(
    val name: String = ""
) {
    companion object {
        private const val GOOGLE_PLAY_STORE_NAME = "com.android.vending"
        private const val ONE_STORE_NAME = "com.skt.skaf.A000Z00040"
        private const val GAXALAXY_STORE_NAME = "com.sec.android.app.samsungapps"

        fun from(packageName: String?): StoreName {
            return when (packageName) {
                GOOGLE_PLAY_STORE_NAME -> StoreName("Google Play")
                ONE_STORE_NAME -> StoreName("One Store")
                GAXALAXY_STORE_NAME -> StoreName("Galaxy Store")
                else -> StoreName()
            }
        }
    }
}