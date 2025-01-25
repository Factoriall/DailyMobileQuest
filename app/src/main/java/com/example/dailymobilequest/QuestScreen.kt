package com.example.dailymobilequest

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dailymobilequest.ui.theme.DailyMobileQuestTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun QuestScreen(
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope? = null,
) {
    Column {
        with(sharedTransitionScope) {
            QuestButton(
                modifier = Modifier
                    .then(
                        if (animatedContentScope != null) Modifier.sharedElement(
                            rememberSharedContentState(key = "quest_title"),
                            animatedVisibilityScope = animatedContentScope
                        ) else Modifier
                    ),
                onQuestsButtonClicked = { }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun QuestScreenPreview() {
    DailyMobileQuestTheme {
        SharedTransitionLayout { QuestScreen(sharedTransitionScope = this@SharedTransitionLayout) }
    }
}