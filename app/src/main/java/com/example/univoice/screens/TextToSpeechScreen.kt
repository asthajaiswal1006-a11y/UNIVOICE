package com.example.univoice.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.univoice.TranslationHelper
import java.util.Locale

@Composable
fun TextToSpeechScreen() {
    val context = LocalContext.current

    var text by remember { mutableStateOf("") }
    var translatedText by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf("Hindi") }
    var menuExpanded by remember { mutableStateOf(false) }
    var isReady by remember { mutableStateOf(false) }
    var isTranslating by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var speaker by remember { mutableStateOf<TextToSpeech?>(null) }

    val translationHelper = remember { TranslationHelper() }

    DisposableEffect(context) {
        val tts = TextToSpeech(context) { status ->
            isReady = status == TextToSpeech.SUCCESS
        }

        speaker = tts

        onDispose {
            tts.stop()
            tts.shutdown()
            speaker = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Text to Speech & Translation",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Enter English text, translate it, and listen to the result.")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter English text") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )


        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val result = speaker?.setLanguage(Locale.US)

                if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    message = "English voice data is not available on this device."
                } else {
                    speaker?.speak(
                        text,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "univoice_english"
                    )
                }
            },
            enabled = isReady && text.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🔊 Speak English")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Translate into:")

        TextButton(onClick = { menuExpanded = true }) {
            Text("$selectedLanguage ▼")
        }

        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Hindi") },
                onClick = {
                    selectedLanguage = "Hindi"
                    menuExpanded = false
                    translatedText = ""
                    message = ""
                }
            )

            DropdownMenuItem(
                text = { Text("Marathi") },
                onClick = {
                    selectedLanguage = "Marathi"
                    menuExpanded = false
                    translatedText = ""
                    message = ""
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                isTranslating = true
                translatedText = ""
                message = ""

                val onSuccess: (String) -> Unit = { result ->
                    translatedText = result
                    isTranslating = false
                    message = "Translation complete."
                }

                val onFailure: (String) -> Unit = { error ->
                    isTranslating = false
                    message = error
                }

                if (selectedLanguage == "Hindi") {
                    translationHelper.translateToHindi(
                        text.trim(), onSuccess, onFailure
                    )
                } else {
                    translationHelper.translateToMarathi(
                        text.trim(), onSuccess, onFailure
                    )
                }
            },
            enabled = text.isNotBlank() && !isTranslating,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Translate")
        }

        if (isTranslating) {
            Spacer(modifier = Modifier.height(12.dp))
            CircularProgressIndicator()
            Text("Preparing language model and translating...")
        }

        if (translatedText.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Translated text ($selectedLanguage)",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = translatedText,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val language = if (selectedLanguage == "Hindi") {
                        Locale("hi", "IN")
                    } else {
                        Locale("mr", "IN")
                    }

                    val result = speaker?.setLanguage(language)
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED
                    ) {
                        message = "$selectedLanguage speech is not supported on this device. Install its voice data in Text-to-Speech settings."
                    } else {
                        speaker?.speak(
                            translatedText,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            "univoice_translated"
                        )
                    }
                },
                enabled = isReady,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔊 Speak Translation")
            }
        }

        if (message.isNotBlank()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(message)
        }
    }
}