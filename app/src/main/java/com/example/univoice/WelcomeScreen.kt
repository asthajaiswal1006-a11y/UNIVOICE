
package com.example.univoice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val WelcomeTeal = Color(0xFF287F73)
private val WelcomeLavender = Color(0xFFEDE5FA)

@Composable
fun WelcomeScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onGuestClick: () -> Unit
) {
    var showGuide by remember { mutableStateOf(true) }
    var showHelpMenu by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8F7FC)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "UNIVOICE",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = WelcomeTeal
            )

            Text(
                text = "Every Voice Matters 💜",
                fontSize = 16.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(
                    id = R.drawable.univoice_mascot
                ),
                contentDescription = "UNIVOICE guide mascot",
                modifier = Modifier.size(190.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                shape = RoundedCornerShape(22.dp),
                color = WelcomeLavender,
                shadowElevation = 3.dp
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (showGuide) {
                            "Hi there! 👋 Tap me!"
                        } else {
                            "Welcome to UNIVOICE! 💜"
                        },
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        color = WelcomeTeal
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = if (showGuide) {
                            "I'm your friendly guide. I'll help you get started!"
                        } else {
                            "Register, log in, or explore as a guest."
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        color = Color(0xFF343044)
                    )

                    TextButton(
                        onClick = { showHelpMenu = true }
                    ) {
                        Text("Tap me for help ✨")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WelcomeTeal
                )
            ) {
                Text("Register")
            }

            OutlinedButton(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Login")
            }

            TextButton(onClick = onGuestClick) {
                Text("Continue as Guest")
            }
        }
    }

    if (showHelpMenu) {
        AlertDialog(
            onDismissRequest = { showHelpMenu = false },
            title = {
                Text("Hi! I'm your UNIVOICE guide 💜")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("What would you like help with?")

                    OutlinedButton(
                        onClick = {
                            showHelpMenu = false
                            onRegisterClick()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("📝 Register — Create an account")
                    }

                    OutlinedButton(
                        onClick = {
                            showHelpMenu = false
                            onLoginClick()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🔐 Login — Access your account")
                    }

                    OutlinedButton(
                        onClick = {
                            showHelpMenu = false
                            onGuestClick()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("👋 Continue as Guest")
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showHelpMenu = false }
                ) {
                    Text("Close")
                }
            }
        )
    }

}
