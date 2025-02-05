package com.example.dailymobilequest.presentation

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dailymobilequest.R

/**
 * 앱 관련 스케쥴을 정하는 화면
 */
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    uiModel: QuestAppDetailUiModel
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            AsyncImage(
                uiModel.iconDrawable,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .size(100.dp),
                placeholder = painterResource(R.drawable.ic_launcher_background)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    uiModel.title,
                    style = MaterialTheme.typography.headlineLarge,
                    maxLines = 1
                )
                Text(
                    uiModel.storeName,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
        }

        LazyColumn {
            items(uiModel.questList) {

            }
        }
    }
}


data class QuestAppDetailUiModel(
    val title: String = "",
    val iconDrawable: Drawable? = null,
    val packageName: String = "",
    val storeName: String = "",
    val questList: List<QuestUiModel> = listOf(),
) {
}

data class QuestUiModel(
    val questName: String,
    val category: String,
    val description: String
) {

}

@Composable
@Preview
fun DetailScreenPreview() {
    DetailScreen(
        uiModel = QuestAppDetailUiModel(
            title = "TestApp",
            packageName = "testtest",
            storeName = "Google Play",
            iconDrawable = ColorDrawable(0xFF000000.toInt()),
        )
    )
}