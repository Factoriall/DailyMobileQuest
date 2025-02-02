package com.example.dailymobilequest.presentation

import androidx.compose.runtime.Composable

/**
 * 앱 관련 스케쥴을 정하는 화면
 */
@Composable
fun DetailScreen() {

}

data class QuestAppDetailUiModel(
    val title: String,
    val packageName: String,
    val storeName: String,
    val questList: List<QuestUiModel>,
) {
}

data class QuestUiModel(
    val questName: String,
    val category: String,
    val description: String,

    ) {

}