package com.example.dailymobilequest.presentation

import android.graphics.drawable.Drawable
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import coil.compose.AsyncImage
import com.example.dailymobilequest.R
import com.example.dailymobilequest.data.DayOfMonth
import com.example.dailymobilequest.data.DayOfWeek
import com.example.dailymobilequest.data.Frequency
import kotlinx.coroutines.launch
import java.time.Instant

/**
 * 하나의 앱 퀘스트 모음 관련 뷰모델
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppQuestScreen(
    modifier: Modifier = Modifier,
    uiModel: QuestAppDetailUiModel,
    onEditButtonClicked: (id: Long) -> Unit = {},
    onSaveButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: (id: Long) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var isSheetVisible by remember { mutableStateOf(false) }

    Box {
        Column(modifier = modifier.fillMaxSize()) {
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

                    Button(
                        onClick = onSaveButtonClicked,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("저장")
                    }
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 66.dp, start = 8.dp, end = 8.dp)
            ) {
                items(uiModel.questList) { item ->
                    QuestTile(item, onEditButtonClicked = {
                        onEditButtonClicked(item.id)
                    }, onDeleteButtonClicked = {
                        onDeleteButtonClicked(item.id)
                    })
                }
            }
        }

        FloatingActionButton(
            onClick = {
                scope.launch {
                    isSheetVisible = true
                    sheetState.show()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .navigationBarsPadding()
                .padding(bottom = 8.dp, end = 8.dp)
                .size(50.dp),
            shape = CircleShape
        ) {
            Image(Icons.Default.Add, contentDescription = null)
        }

        if (isSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                        isSheetVisible = false
                    }
                }
            ) {
                // TODO: BottomSheet 내용 채우기
                Text("Example BottomSheet")
            }
        }
    }
}


@Composable
private fun QuestTile(
    questUiModel: QuestUiModel,
    onEditButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: () -> Unit = {},
) {
    val dismissState = rememberSwipeToDismissBoxState()

    LaunchedEffect(dismissState) {
        snapshotFlow { dismissState.currentValue to dismissState.targetValue }
            .collect { (current, target) ->
                if (current != SwipeToDismissBoxValue.StartToEnd && target == SwipeToDismissBoxValue.StartToEnd) {
                    // 드래그가 끝나서 StartToEnd 상태로 정착한 경우
                    onEditButtonClicked()
                } else if (current != SwipeToDismissBoxValue.EndToStart && target == SwipeToDismissBoxValue.EndToStart) {
                    onDeleteButtonClicked()
                }
            }
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by
            animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> Color.LightGray
                    SwipeToDismissBoxValue.StartToEnd -> Color.Green
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                }, label = ""
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color, RoundedCornerShape(8.dp))
            ) {
                if (dismissState.targetValue == SwipeToDismissBoxValue.StartToEnd) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier
                            .align(
                                Alignment.CenterStart
                            )
                            .padding(start = 8.dp)
                    )
                } else if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                    )
                }
            }
        }
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
)

data class QuestUiModel(
    val id: Long = 0,
    val questName: String,
    val description: String,
    val startDate: Instant? = null,
    val endDate: Instant? = null,
    val frequency: Frequency,
)

@Composable
@Preview
fun AppQuestScreenPreview() {
    AppQuestScreen(
        uiModel = QuestAppDetailUiModel(
            title = "TestApp",
            packageName = "testtest",
            storeName = "Google Play",
            iconDrawable = 0xFF000000.toInt().toDrawable(),
            questList = listOf(
                QuestUiModel(
                    questName = "Test Quest 1",
                    description = "Test Description 1",
                    frequency = Frequency.Monthly(listOf(DayOfMonth(1), DayOfMonth(2))),
                ),
                QuestUiModel(
                    questName = "Test Quest 2",
                    description = "Test Description 2",
                    frequency = Frequency.Weekly(listOf(DayOfWeek.MON, DayOfWeek.TUE)),
                ),

                QuestUiModel(
                    questName = "Test Quest 3",
                    description = "Test Description 3",
                    frequency = Frequency.Daily(1),
                ),
                QuestUiModel(
                    questName = "Test Quest 4",
                    description = "Test Description 4",
                    frequency = Frequency.FlexibleWeekly(
                        perWeek = 3,
                        startDayOfWeek = DayOfWeek.MON,
                        dayOfWeeks = listOf(1, 2, 3, 4, 5)
                    ),
                ),
            )
        )
    )
}