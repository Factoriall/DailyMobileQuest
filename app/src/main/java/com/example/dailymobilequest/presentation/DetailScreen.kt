package com.example.dailymobilequest.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable

/**
 * 클래스에 대한 간단한 설명이나 참고 url을 남겨주세요.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
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