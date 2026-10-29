
package com.example.univoice

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

class TranslationHelper {

    fun translateToHindi(
        text: String,
        onResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        translate(
            text = text,
            targetLanguage = TranslateLanguage.HINDI,
            onResult = onResult,
            onError = onError
        )
    }

    fun translateToMarathi(
        text: String,
        onResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        translate(
            text = text,
            targetLanguage = TranslateLanguage.MARATHI,
            onResult = onResult,
            onError = onError
        )
    }

    private fun translate(
        text: String,
        targetLanguage: String,
        onResult: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        if (text.isBlank()) {
            onError("Please enter some text.")
            return
        }

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(targetLanguage)
            .build()

        val translator: Translator = Translation.getClient(options)

        val conditions = com.google.mlkit.common.model.DownloadConditions.Builder()
            .build()

        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { translatedText ->
                        onResult(translatedText)
                        translator.close()
                    }
                    .addOnFailureListener { exception ->
                        onError(exception.localizedMessage ?: "Translation failed.")
                        translator.close()
                    }
            }
            .addOnFailureListener { exception ->
                onError(
                    exception.localizedMessage
                        ?: "Language model download failed. Please connect to Wi-Fi."
                )
                translator.close()
            }
    }
}