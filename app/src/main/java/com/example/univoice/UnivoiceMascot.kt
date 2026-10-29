
package com.example.univoice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*

private val MascotTeal = Color(0xFF287F73)
private val MascotLavender = Color(0xFFEDE5FA)
private val MascotCream = Color(0xFFFFF9F0)

@Composable
fun UnivoiceMascot(
    message: String = "Hi! I'm your UNIVOICE guide. Let me help you get started!",
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
){
    var showHelp by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
                showHelp = true
            },
        shape = RoundedCornerShape(24.dp),
        color = MascotLavender,
        shadowElevation = 3.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Spacer(modifier = Modifier.size(4.dp))

            Image(
                painter = painterResource(id = R.drawable.univoice_mascot),
                contentDescription = "UNIVOICE guide mascot",
                modifier = Modifier.size(110.dp),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Hi, friend! 💜",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MascotTeal
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF343044)
                )
            }

            Spacer(modifier = Modifier.size(8.dp))
        }
    }
    if (showHelp) {
        AlertDialog(
            onDismissRequest = { showHelp = false },
            title = {
                Text("Hi, friend! 💜")
            },
            text = {
                Column {
                    Text("I'm your UNIVOICE guide!")
                    Text("Choose what you need help with:")
                    Spacer(modifier = Modifier.size(12.dp))

                    listOf(
                        "Register",
                        "Login",
                        "Gesture Recognition",
                        "Sentence Builder",
                        "Translation",
                        "History"
                    ).forEach { topic ->
                        TextButton(
                            onClick = {
                                showHelp = false
                            }
                        ) {
                            Text(topic)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showHelp = false }
                ) {
                    Text("Close")
                }
            }
        )
    }
}
