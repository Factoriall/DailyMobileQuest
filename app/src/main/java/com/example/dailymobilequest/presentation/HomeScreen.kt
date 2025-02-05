package com.example.dailymobilequest.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailymobilequest.ui.theme.DailyMobileQuestTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onStartButtonClicked: () -> Unit = {},
    onQuestsButtonClicked: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircleButton(
            modifier = Modifier.align(Alignment.Center),
            onStartButtonClicked = onStartButtonClicked
        )

        QuestButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onQuestsButtonClicked = onQuestsButtonClicked
        )
    }
}

@Composable
fun QuestButton(
    modifier: Modifier = Modifier,
    onQuestsButtonClicked: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(bottom = 80.dp)
            .clickable {
                onQuestsButtonClicked()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.List,
            contentDescription = "Quests",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "퀘스트 목록",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@Composable
fun CircleButton(modifier: Modifier, onStartButtonClicked: () -> Unit) {
    Box(
        modifier = modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onStartButtonClicked() },
    ) {
        Text(
            text = "START",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    DailyMobileQuestTheme {
        HomeScreen()
    }
}