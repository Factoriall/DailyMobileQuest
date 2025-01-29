package com.example.dailymobilequest

import android.content.Intent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailymobilequest.data.QuestData
import com.example.dailymobilequest.data.Screen
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

                            composable(route = Screen.DETAIL.name) {
                                DetailScreen(
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedContentScope = this@composable,
                                )
                            }

                            composable(route = Screen.APP_LIST.name) {
                                val context = LocalContext.current
                                val packageManager = context.packageManager
                                val intent = Intent(Intent.ACTION_MAIN, null).apply {
                                    addCategory(Intent.CATEGORY_LAUNCHER)
                                }
                                val apps = packageManager.queryIntentActivities(intent, 0)

                                AppListScreen(
                                    appList = apps.map {
                                        QuestData(
                                            appName = it.loadLabel(packageManager).toString(),
                                            iconDrawable = it.loadIcon(packageManager),
                                            packageName = it.activityInfo.packageName
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}