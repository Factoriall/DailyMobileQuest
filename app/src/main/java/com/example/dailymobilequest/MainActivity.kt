package com.example.dailymobilequest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dailymobilequest.data.Screen
import com.example.dailymobilequest.presentation.AppListScreen
import com.example.dailymobilequest.presentation.AppListViewModel
import com.example.dailymobilequest.presentation.AppQuestScreen
import com.example.dailymobilequest.presentation.AppQuestViewModel
import com.example.dailymobilequest.presentation.HomeScreen
import com.example.dailymobilequest.presentation.QuestListScreen
import com.example.dailymobilequest.ui.theme.DailyMobileQuestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyMobileQuestTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry?.destination?.route

                val canGoBack = navController.previousBackStackEntry != null

                val title = when {
                    currentDestination == Screen.QUEST.name -> "퀘스트 모음"
                    currentDestination == Screen.APP_LIST.name -> "설치 앱 목록"
                    currentDestination?.startsWith(Screen.DETAIL.name) == true -> "앱 루틴 정하기"
                    else -> "Daily Mobile Quest"
                }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = title)
                            },
                            navigationIcon = if (canGoBack) {
                                {
                                    IconButton(onClick = {
                                        navController.popBackStack()
                                    }) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                                    }
                                }
                            } else {
                                {}
                            },
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.White,
                            )
                        )
                    }
                ) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)

                    NavHost(
                        navController = navController,
                        startDestination = Screen.HOME.name
                    ) {
                        composable(route = Screen.HOME.name) {
                            HomeScreen(
                                modifier = modifier,
                                onQuestsButtonClicked = {
                                    navController.navigate(Screen.QUEST.name)
                                }
                            )
                        }

                        composable(route = Screen.QUEST.name) {
                            QuestListScreen(
                                modifier = modifier,
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
                            val viewModel: AppQuestViewModel = hiltViewModel()
                            val uiState = viewModel.uiModel.collectAsState()
                            AppQuestScreen(
                                modifier = modifier,
                                uiState.value,
                                onSaveButtonClicked = {
                                    viewModel.saveData()
                                }
                            )
                        }

                        composable(route = Screen.APP_LIST.name) {
                            val viewModel: AppListViewModel = hiltViewModel()
                            val uiState = viewModel.uiModel.collectAsState()

                            AppListScreen(modifier = modifier, uiState.value) { app ->
                                navController.navigate("${Screen.DETAIL.name}/${app.packageName}")
                            }
                        }
                    }
                }
            }
        }
    }
}