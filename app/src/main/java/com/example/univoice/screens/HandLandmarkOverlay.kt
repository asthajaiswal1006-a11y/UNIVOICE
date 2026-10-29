package com.example.univoice.screens

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import com.google.mediapipe.tasks.vision.gesturerecognizer.GestureRecognizerResult
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker

class HandLandmarkOverlay(
    context: Context
) : View(context) {

    private var result: GestureRecognizerResult? = null
    private var imageWidth = 1
    private var imageHeight = 1

    private val linePaint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 6f
        style = Paint.Style.STROKE
        isAntiAlias = true
    }

    private val pointPaint = Paint().apply {
        color = Color.YELLOW
        strokeWidth = 12f
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    fun setResults(
        result: GestureRecognizerResult,
        imageWidth: Int,
        imageHeight: Int
    ) {
        this.result = result
        this.imageWidth = imageWidth
        this.imageHeight = imageHeight
        invalidate()
    }

    fun clear() {
        result = null
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val currentResult = result ?: return

        val landmarks = currentResult.landmarks()

        if (landmarks.isEmpty()) {
            return
        }

        val scaleX = width.toFloat() / imageWidth
        val scaleY = height.toFloat() / imageHeight

        for (hand in landmarks) {

            // Draw the 21 landmark points
            for (landmark in hand) {

                val x = landmark.x() * imageWidth * scaleX
                val y = landmark.y() * imageHeight * scaleY

                canvas.drawCircle(
                    x,
                    y,
                    7f,
                    pointPaint
                )
            }

            // Draw the hand skeleton
            for (connection in HandLandmarker.HAND_CONNECTIONS) {

                val start = hand.getOrNull(
                    connection.start()
                )

                val end = hand.getOrNull(
                    connection.end()
                )

                if (start != null && end != null) {

                    val startX =
                        start.x() * imageWidth * scaleX

                    val startY =
                        start.y() * imageHeight * scaleY

                    val endX =
                        end.x() * imageWidth * scaleX

                    val endY =
                        end.y() * imageHeight * scaleY

                    canvas.drawLine(
                        startX,
                        startY,
                        endX,
                        endY,
                        linePaint
                    )
                }
            }
        }
    }
}