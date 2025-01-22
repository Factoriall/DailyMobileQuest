package com.example.dailymobilequest

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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