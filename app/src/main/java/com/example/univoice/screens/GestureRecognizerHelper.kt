package com.example.univoice.screens

import android.content.Context
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.gesturerecognizer.GestureRecognizer
import com.google.mediapipe.tasks.vision.gesturerecognizer.GestureRecognizerResult
import java.nio.ByteBuffer
import java.nio.ByteOrder


class GestureRecognizerHelper(context: Context) {

    private val recognizer: GestureRecognizer

    init {
        val modelName = context.assets
            .list("models")
            ?.firstOrNull()
            ?: throw IllegalStateException("No model found in assets/models")

        val modelBuffer = loadModelBuffer(
            context,
            "models/$modelName"
        )

        val baseOptions = BaseOptions.builder()
            .setModelAssetBuffer(modelBuffer)
            .build()

        val options =
            GestureRecognizer.GestureRecognizerOptions.builder()
                .setBaseOptions(baseOptions)
                .setRunningMode(RunningMode.IMAGE)
                .setNumHands(2)
                .build()

        recognizer =
            GestureRecognizer.createFromOptions(
                context,
                options
            )
    }

    private fun loadModelBuffer(
        context: Context,
        assetPath: String
    ): ByteBuffer {

        val bytes = context.assets
            .open(assetPath)
            .use { it.readBytes() }

        val buffer = ByteBuffer
            .allocateDirect(bytes.size)
            .order(ByteOrder.nativeOrder())

        buffer.put(bytes)
        buffer.rewind()

        return buffer
    }

    fun recognize(
        bitmap: android.graphics.Bitmap
    ): GestureRecognizerResult {

        val mpImage =
            BitmapImageBuilder(bitmap).build()

        return recognizer.recognize(mpImage)
    }

    fun close() {
        recognizer.close()
    }
}