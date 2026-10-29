package com.example.univoice.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.univoice.ui.theme.AppBackground

@Composable
fun MainAppScreen(
    userName: String,
    userEmail: String,
    onLogout: () -> Unit
) {

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = AppBackground,
        bottomBar = {

            NavigationBar {

                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Recognize"
                        )
                    },
                    label = {
                        Text("Recognize")
                    }
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History"
                        )
                    },
                    label = {
                        Text("History")
                    }
                )

                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {


            when (selectedTab) {
                0 -> HomeScreen(
                    userName = userName,
                    onNavigateToTab = { tab ->
                        selectedTab = tab
                    }
                )

                1 -> RecognizeScreen()

                2 -> HistoryScreen()

                3 -> ProfileScreen(
                    userName = userName,
                    userEmail = userEmail,
                    onLogout = onLogout
                )

                4 -> SentenceBuilderScreen()

                5 -> TextToSpeechScreen()

            }
        }
    }
}
