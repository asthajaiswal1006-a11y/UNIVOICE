package com.example.univoice.screens

import android.content.Context

object GestureHistory {

    private const val PREFS_NAME = "univoice_history"
    private const val KEY_HISTORY = "recognized_gestures"

    fun save(context: Context, gesture: String) {
        if (
            gesture.isBlank() ||
            gesture == "Show your hand to the camera." ||
            gesture == "None"
        ) {
            return
        }

        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val currentHistory =
            prefs.getString(KEY_HISTORY, "")
                ?.split("|")
                ?.filter { it.isNotBlank() }
                ?: emptyList()

        val updatedHistory =
            (currentHistory + gesture).takeLast(50)

        prefs.edit()
            .putString(
                KEY_HISTORY,
                updatedHistory.joinToString("|")
            )
            .apply()
    }

    fun getAll(context: Context): List<String> {
        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        return prefs.getString(KEY_HISTORY, "")
            ?.split("|")
            ?.filter { it.isNotBlank() }
            ?.reversed()
            ?: emptyList()
    }
}