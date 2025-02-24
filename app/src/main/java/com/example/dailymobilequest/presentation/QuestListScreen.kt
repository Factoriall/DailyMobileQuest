package com.example.dailymobilequest.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailymobilequest.data.ApplicationProfile
import com.example.dailymobilequest.ui.theme.DailyMobileQuestTheme

@Composable
fun QuestListScreen(
    modifier: Modifier = Modifier,
    onClickAddButton: () -> Unit = {},
    onClickAppButton: () -> Unit = {}
) {
    Column(modifier = modifier) {
        val questList = listOf<ApplicationProfile>()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(questList) { quest ->

            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.Center)
                            .clickable {
                                onClickAddButton()
                            },
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }

}

@Composable
@Preview
fun QuestListScreenPreview() {
    DailyMobileQuestTheme {
        QuestListScreen()
    }
}