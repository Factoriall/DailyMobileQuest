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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailymobilequest.data.Screen
import com.example.dailymobilequest.data.StoreName
import com.example.dailymobilequest.presentation.AppListScreen
import com.example.dailymobilequest.presentation.AppListScreenUiModel
import com.example.dailymobilequest.presentation.DetailScreen
import com.example.dailymobilequest.presentation.HomeScreen
import com.example.dailymobilequest.presentation.QuestAppUiModel
import com.example.dailymobilequest.presentation.QuestScreen
import com.example.dailymobilequest.ui.theme.DailyMobileQuestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
                                DetailScreen()
                            }

                            composable(route = Screen.APP_LIST.name) {
                                var uiModel by remember<MutableState<AppListScreenUiModel>> {
                                    mutableStateOf(AppListScreenUiModel.Loading)
                                }
                                val context = LocalContext.current
                                val packageManager = context.packageManager
                                val intent = Intent(Intent.ACTION_MAIN, null).apply {
                                    addCategory(Intent.CATEGORY_LAUNCHER)
                                }

                                LaunchedEffect(Unit) {
                                    val appList = withContext(Dispatchers.IO) {
                                        packageManager.queryIntentActivities(intent, 0).map {
                                            val storeName = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                                                packageManager.getInstallSourceInfo(it.activityInfo.packageName).installingPackageName
                                            } else {
                                                packageManager.getInstallerPackageName(it.activityInfo.packageName)
                                            }
                                            QuestAppUiModel(
                                                appName = it.loadLabel(packageManager).toString(),
                                                iconDrawable = it.loadIcon(packageManager),
                                                packageName = it.activityInfo.packageName,
                                                storeName = StoreName.from(storeName)
                                            )
                                        }
                                    }

                                    uiModel = AppListScreenUiModel.Loaded(appList)
                                }

                                AppListScreen(uiModel) {
                                    navController.navigate(Screen.DETAIL.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}