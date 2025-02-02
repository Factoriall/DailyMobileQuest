package com.example.dailymobilequest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dailymobilequest.data.Screen
import com.example.dailymobilequest.presentation.AppListScreen
import com.example.dailymobilequest.presentation.AppListViewModel
import com.example.dailymobilequest.presentation.DetailScreen
import com.example.dailymobilequest.presentation.QuestDetailViewModel
import com.example.dailymobilequest.presentation.HomeScreen
import com.example.dailymobilequest.presentation.QuestScreen
import com.example.dailymobilequest.ui.theme.DailyMobileQuestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyMobileQuestTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background)
                ) { innerPadding ->
                    val navController = rememberNavController()
                    SharedTransitionLayout {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.HOME.name,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(route = Screen.HOME.name) {
                                HomeScreen(
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedContentScope = this@composable,
                                    onQuestsButtonClicked = {
                                        navController.navigate(Screen.QUEST.name)
                                    }
                                )
                            }

                            composable(route = Screen.QUEST.name) {
                                QuestScreen(
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedContentScope = this@composable,
                                    onClickAddButton = {
                                        navController.navigate(Screen.APP_LIST.name)
                                    }
                                )
                            }

                            composable(
                                route = "${Screen.DETAIL.name}/{packageName}", arguments = listOf(
                                    navArgument("packageName") { type = NavType.StringType },
                                )
                            ) {
                                val viewModel: QuestDetailViewModel = hiltViewModel()
                                val uiState = viewModel.uiModel.collectAsState()
                                DetailScreen(uiState.value)
                            }

                            composable(route = Screen.APP_LIST.name) {
                                val viewModel: AppListViewModel = hiltViewModel()
                                val uiState = viewModel.uiModel.collectAsState()

                                AppListScreen(uiState.value) { app ->
                                    navController.navigate("${Screen.DETAIL.name}/${app.packageName}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}