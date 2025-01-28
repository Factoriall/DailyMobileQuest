package com.example.dailymobilequest

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.dailymobilequest.data.QuestData

/**
 * 설치 가능한 앱 리스트 항목을 보여주는 화면
 */
@Composable
fun AppListScreen(appList: List<QuestData>) {

    LazyColumn {
        items(appList) { app ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Image(
                    rememberAsyncImagePainter(app.iconDrawable),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(text = app.appName, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }

}

@Composable
@Preview
fun AppListScreenPreview() {
    AppListScreen(
        listOf(
            QuestData(
                appName = "App 1",
                iconDrawable = ColorDrawable(0xFF000000.toInt()),
                packageName = "com.example.app1"
            ),
            QuestData(
                appName = "App 2",
                iconDrawable = ColorDrawable(0xFF111111.toInt()),
                packageName = "com.example.app2"
            ),
            QuestData(
                appName = "App 3",
                iconDrawable = ColorDrawable(0xFF999999.toInt()),
                packageName = "com.example.app3"
            ),
        )
    )
}