package com.example.dailymobilequest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailymobilequest.ui.theme.DailyMobileQuestTheme

class MainActivity : ComponentActivity() {
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
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HOME.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.HOME.name) {
                            HomeScreen(
                                onQuestsButtonClicked = {
                                    navController.navigate(Screen.QUEST.name)
                                }
                            )
                        }

                        composable(route = Screen.QUEST.name) {
                            QuestScreen()
                        }
                    }
                }
            }
        }
    }
}