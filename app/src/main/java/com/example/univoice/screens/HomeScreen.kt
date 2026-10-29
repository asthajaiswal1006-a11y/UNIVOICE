package com.example.univoice.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univoice.components.FeatureCard
import com.example.univoice.ui.theme.AppBackground
import com.example.univoice.ui.theme.DarkText
import com.example.univoice.ui.theme.PrimaryBlue
import com.example.univoice.ui.theme.SecondaryText
import androidx.compose.ui.Alignment
import com.example.univoice.UnivoiceMascot
import androidx.compose.runtime.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.HorizontalDivider
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import com.example.univoice.R


@Composable
fun HomeScreen(
    userName: String,
    onNavigateToTab: (Int) -> Unit
)  {
    var showHelpMenu by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Hello, $userName 👋",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Welcome back to UNIVOICE",
                color = SecondaryText,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            UnivoiceMascot(
                message = "Welcome to UNIVOICE! 💜 Tap me for help!",
                onClick = {
                    showHelpMenu = true
                }
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = PrimaryBlue
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .background(
                                    Color.White.copy(alpha = 0.18f),
                                    RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.RecordVoiceOver,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = "Every Voice Matters",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "UNIVOICE",
                                color = Color.White.copy(alpha = 0.75f),
                                fontSize = 13.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Communicate using Indian Sign Language with technology.",
                        color = Color.White.copy(alpha = 0.92f),
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Quick Actions",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "Choose a feature to get started",
                color = SecondaryText,
                fontSize = 14.sp
            )
        }

        item {
            FeatureCard(
                icon = Icons.Default.CameraAlt,
                title = "Gesture Recognition",
                description = "Recognize ISL gestures using your camera.",
                onClick = {
                    onNavigateToTab(1)
                }
            )
        }

        item {
            FeatureCard(
                icon = Icons.Default.History,
                title = "Recognition History",
                description = "View your previously recognized sentences.",
                onClick = {
                    onNavigateToTab(2)
                }
            )
        }

        item {

            FeatureCard(
                icon = Icons.Default.TextFields,
                title = "Sentence Builder",
                description = "Build meaningful sentences from recognized words.",
                onClick = {
                    onNavigateToTab(4)
                }
            )
        }

        item {

            FeatureCard(
                icon = Icons.Default.VolumeUp,
                title = "Text to Speech",
                description = "Convert recognized text into spoken output.",
                onClick = {
                    onNavigateToTab(5)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }



    if (showHelpMenu) {
        var selectedHelp by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showHelpMenu = false },
            title = {
                Text("Hi, friend! 💜")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.univoice_mascot
                            ),
                            contentDescription = "Your UNIVOICE guide",
                            modifier = Modifier.size(90.dp)
                        )

                        Column {
                            Text(
                                text = when (selectedHelp) {
                                    "Gesture Recognition" ->
                                        "Let's recognize gestures! 👋"

                                    "Sentence Builder" ->
                                        "Let's build a sentence! ✍️"

                                    "Text-to-Speech" ->
                                        "Let's speak your words! 🔊"

                                    "History" ->
                                        "Let's look at your history! 🕘"

                                    else ->
                                        "Hi, friend! 💜"
                                },
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = when (selectedHelp) {
                                    "Gesture Recognition" ->
                                        "Follow my steps. You can do it!"

                                    "Sentence Builder" ->
                                        "I'll help you express your thoughts."

                                    "Text-to-Speech" ->
                                        "I'll show you how to speak your text."

                                    "History" ->
                                        "Let's find your saved results."

                                    else ->
                                        "Choose a feature and I'll guide you!"
                                }
                            )
                        }
                    }

                    HorizontalDivider()

                    if (selectedHelp.isEmpty()) {
                        Text("Let's learn how to use UNIVOICE!")

                        TextButton(onClick = {
                            selectedHelp = "Gesture Recognition"
                        }) {
                            Text("📷 How to use Gesture Recognition")
                        }

                        TextButton(onClick = {
                            selectedHelp = "Sentence Builder"
                        }) {
                            Text("✍️ How to use Sentence Builder")
                        }

                        TextButton(onClick = {
                            selectedHelp = "Text-to-Speech"
                        }) {
                            Text("🔊 How to use Text-to-Speech")
                        }

                        TextButton(onClick = {
                            selectedHelp = "History"
                        }) {
                            Text("🕘 How to use History")
                        }
                    } else {
                        Text(
                            when (selectedHelp) {
                                "Gesture Recognition" ->
                                    "📷 Gesture Recognition\n\n" +
                                            "1. Open Gesture Recognition.\n" +
                                            "2. Allow camera permission.\n" +
                                            "3. Show your hand to the camera.\n" +
                                            "4. Wait for the result.\n" +
                                            "5. Listen to the spoken word."

                                "Sentence Builder" ->
                                    "✍️ Sentence Builder\n\n" +
                                            "1. Open Sentence Builder.\n" +
                                            "2. Follow the options on the screen.\n" +
                                            "3. Build your sentence.\n" +
                                            "4. Check the sentence you create."

                                "Text-to-Speech" ->
                                    "🔊 Text-to-Speech\n\n" +
                                            "1. Open Text-to-Speech.\n" +
                                            "2. Type your message.\n" +
                                            "3. Tap the Speak button.\n" +
                                            "4. Listen to your message."

                                else ->
                                    "🕘 History\n\n" +
                                            "1. Open History.\n" +
                                            "2. Look through saved results.\n" +
                                            "3. Find a result you need."
                            }
                        )

                        TextButton(onClick = {
                            selectedHelp = ""
                        }) {
                            Text("← Back to guide")
                        }

                        TextButton(onClick = {
                            val tab = when (selectedHelp) {
                                "Gesture Recognition" -> 1
                                "Sentence Builder" -> 4
                                "Text-to-Speech" -> 5
                                "History" -> 2
                                else -> 0
                            }
                            showHelpMenu = false
                            onNavigateToTab(tab)
                        }) {
                            Text("Try it now →")
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    showHelpMenu = false
                }) {
                    Text("Close")
                }
            }
        )
    }

}