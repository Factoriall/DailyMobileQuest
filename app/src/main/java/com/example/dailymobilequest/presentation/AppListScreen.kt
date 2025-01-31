package com.example.dailymobilequest.presentation

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dailymobilequest.R
import com.example.dailymobilequest.data.StoreName

/**
 * 설치 가능한 앱 리스트 항목을 보여주는 화면
 */
@Composable
fun AppListScreen(
    screenModel: AppListScreenUiModel,
    onClick: (QuestAppUiModel) -> Unit = {}
) {
    when (screenModel) {
        AppListScreenUiModel.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is AppListScreenUiModel.Loaded -> {
            AppListLoadedScreen(appList = screenModel.appList, onClick = onClick)
        }
    }
}

@Composable
private fun AppListLoadedScreen(
    appList: List<QuestAppUiModel>,
    onClick: (QuestAppUiModel) -> Unit
) {
    LazyColumn {
        items(appList) { app ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 8.dp)
                    .clickable {
                        onClick(app)
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    app.iconDrawable,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground)
                )

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = app.appName,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    if (app.storeName.name.isNotEmpty()) {
                        Text(
                            text = app.storeName.name,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun AppListScreenPreview() {
    AppListScreen(
        AppListScreenUiModel.Loaded(
            listOf(
                QuestAppUiModel(
                    appName = "App 1",
                    iconDrawable = ColorDrawable(0xFF000000.toInt()),
                    packageName = "com.example.app1"
                ),
                QuestAppUiModel(
                    appName = "App 2",
                    iconDrawable = ColorDrawable(0xFF111111.toInt()),
                    packageName = "com.example.app2"
                ),
                QuestAppUiModel(
                    appName = "App 3",
                    iconDrawable = ColorDrawable(0xFF999999.toInt()),
                    packageName = "com.example.app3"
                ),
            )
        )
    )
}

sealed class AppListScreenUiModel {
    data object Loading : AppListScreenUiModel()
    data class Loaded(val appList: List<QuestAppUiModel>) : AppListScreenUiModel()
}

data class QuestAppUiModel(
    val appName: String,
    val iconDrawable: Drawable,
    val packageName: String,
    val storeName: StoreName = StoreName()
)