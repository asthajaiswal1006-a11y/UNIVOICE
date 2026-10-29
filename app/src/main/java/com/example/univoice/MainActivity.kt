package com.example.univoice

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.univoice.screens.LoginScreen
import com.example.univoice.screens.RegisterScreen
import com.example.univoice.screens.ForgotPasswordScreen
import com.example.univoice.screens.HomeScreen
import com.example.univoice.screens.MainAppScreen
import androidx.compose.ui.unit.sp
import com.example.univoice.ui.theme.UNIVOICETheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UNIVOICEApp()
        }
    }
}

/* ---------------------------------------------------------
   APP
--------------------------------------------------------- */

@Composable
fun UNIVOICEApp() {

    UNIVOICETheme {

        var screen by remember {
            mutableStateOf("welcome")
        }

        var userName by remember {
            mutableStateOf("")
        }

        var userEmail by remember {
            mutableStateOf("")
        }

        var registeredEmail by remember {
            mutableStateOf("")
        }

        var registeredPassword by remember {
            mutableStateOf("")
        }

        when (screen) {

            "welcome" -> {
                WelcomeScreen(
                    onRegisterClick = {
                        screen = "register"
                    },
                    onLoginClick = {
                        screen = "login"
                    },
                    onGuestClick = {
                        userName = "Guest"
                        userEmail = ""
                        screen = "home"
                    }
                )
            }


            "login" -> {
                LoginScreen(
                    registeredEmail = registeredEmail,
                    registeredPassword = registeredPassword,

                    onLoginSuccess = {
                        userName = if (registeredEmail.isNotEmpty()) {
                            registeredEmail.substringBefore("@")
                        } else {
                            "User"
                        }

                        screen = "home"
                    },

                    onRegisterClick = {
                        screen = "register"
                    },

                    onForgotPasswordClick = {
                        screen = "forgot"
                    },

                    onGuestClick = {
                        userName = "Guest"
                        userEmail = ""
                        screen = "home"
                    }
                )
            }

            "register" -> {
                RegisterScreen(

                    onRegistered = { name, email, password ->

                        userName = name
                        userEmail = email

                        registeredEmail = email
                        registeredPassword = password

                        screen = "login"
                    },

                    onBackToLogin = {
                        screen = "welcome"
                    }
                )
            }

            "forgot" -> {
                ForgotPasswordScreen(
                    onBackToLogin = {
                        screen = "login"
                    }
                )
            }

            "home" -> {
                MainAppScreen(
                    userName = userName,
                    userEmail = userEmail,

                    onLogout = {
                        screen = "login"
                    }
                )
            }
        }
    }
}
