package com.example.dailymobilequest.presentation

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.dailymobilequest.R
import com.example.dailymobilequest.data.Alarm
import com.example.dailymobilequest.data.DayOfMonth
import com.example.dailymobilequest.data.DayOfWeek
import com.example.dailymobilequest.data.Frequency
import java.time.Instant

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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(uiModel.questList) { item ->
                QuestTile(item)
            }
        }
    }
}

@Composable
private fun QuestTile(
    questUiModel: QuestUiModel
) {
    val density = LocalDensity.current
    val state = SwipeToDismissBoxState(
        initialValue = SwipeToDismissBoxValue.Settled,
        density = density,
        positionalThreshold = { totalDistance -> totalDistance }
    )
    SwipeToDismissBox(
        state = state,
        backgroundContent = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondary
                )
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = questUiModel.questName, style = MaterialTheme.typography.titleLarge)
                Text(text = questUiModel.description, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = questUiModel.frequency.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = questUiModel.alarm.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
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
    val description: String,
    val startDate: Instant? = null,
    val endDate: Instant? = null,
    val frequency: Frequency,
    val alarm: Alarm
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
            questList = listOf(
                QuestUiModel(
                    questName = "Test Quest 1",
                    description = "Test Description 1",
                    frequency = Frequency.Monthly(listOf(DayOfMonth(1), DayOfMonth(2))),
                    alarm = Alarm.Yes.Notification("10:00")
                ),
                QuestUiModel(
                    questName = "Test Quest 2",
                    description = "Test Description 2",
                    frequency = Frequency.Weekly(2, listOf(DayOfWeek.MON, DayOfWeek.TUE)),
                    alarm = Alarm.No
                ),
                QuestUiModel(
                    questName = "Test Quest 3",
                    description = "Test Description 3",
                    frequency = Frequency.Daily(1),
                    alarm = Alarm.Yes.Sound("12:00")
                )
            )
        )
    )
}