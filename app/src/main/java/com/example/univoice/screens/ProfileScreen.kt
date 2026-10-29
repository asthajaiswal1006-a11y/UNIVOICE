package com.example.univoice.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univoice.ui.theme.AppBackground
import com.example.univoice.ui.theme.DarkText
import com.example.univoice.ui.theme.LightBlue
import com.example.univoice.ui.theme.PrimaryBlue
import com.example.univoice.ui.theme.SecondaryText

@Composable
fun ProfileScreen(
    userName: String,
    userEmail: String,
    onLogout: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "My Profile",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Manage your UNIVOICE account.",
            color = SecondaryText,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .background(
                                color = LightBlue,
                                shape = RoundedCornerShape(22.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = PrimaryBlue,
                            modifier = Modifier.size(38.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(18.dp))

                    Column {

                        Text(
                            text = userName,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkText
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "UNIVOICE User",
                            fontSize = 13.sp,
                            color = SecondaryText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = PrimaryBlue,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(
                            text = "Email",
                            fontSize = 12.sp,
                            color = SecondaryText
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = userEmail,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkText
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "About UNIVOICE",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Text(
                text = "UNIVOICE helps users communicate using Indian Sign Language through gesture recognition, sentence building and text-to-speech.",
                modifier = Modifier.padding(18.dp),
                color = SecondaryText,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Logout",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}