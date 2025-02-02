package com.example.dailymobilequest.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/**
 * 앱 관련 스케쥴을 정하는 화면
 */
@Composable
fun DetailScreen(uiModel: QuestAppDetailUiModel) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            uiModel.packageName,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

data class QuestAppDetailUiModel(
    val title: String = "",
    val packageName: String = "",
    val storeName: String = "",
    val questList: List<QuestUiModel> = listOf(),
) {
}

data class QuestUiModel(
    val questName: String,
    val category: String,
    val description: String,

    ) {

}

@Composable
@Preview
fun DetailScreenPreview() {
    DetailScreen(
        QuestAppDetailUiModel(packageName = "testtest")
    )
}