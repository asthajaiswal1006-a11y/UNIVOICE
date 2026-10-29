package com.example.univoice.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.univoice.ui.theme.AppBackground
import com.example.univoice.ui.theme.DarkText
import com.example.univoice.ui.theme.SecondaryText
import androidx.camera.core.ImageAnalysis
import java.util.concurrent.Executors
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import android.speech.tts.TextToSpeech
import java.util.Locale


@Composable
fun RecognizeScreen() {

    val context = LocalContext.current
    val speechEngine = remember {
        TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Use English speech
            }
        }
    }

    DisposableEffect(speechEngine) {
        speechEngine.language = Locale.US

        onDispose {
            speechEngine.stop()
            speechEngine.shutdown()
        }
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var lensFacing by remember {
        mutableIntStateOf(
            CameraSelector.LENS_FACING_BACK
        )
    }

    var recognizedGesture by remember {
        mutableStateOf("Show your hand to the camera.")
    }

    var confidence by remember {
        mutableStateOf("")
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            hasCameraPermission = granted
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Gesture Recognition",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Show a hand gesture in front of your camera.",
            color = SecondaryText,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        if (hasCameraPermission) {

            CameraPreview(
                lensFacing = lensFacing,

                onGestureDetected = { gesture, score ->
                    recognizedGesture = gesture
                    confidence = score

                    if (gesture != "Show your hand to the camera.") {
                        speechEngine.speak(
                            gesture,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            "UNIVOICE_GESTURE"
                        )
                    }
                },


                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {

                    lensFacing =
                        if (
                            lensFacing ==
                            CameraSelector.LENS_FACING_BACK
                        ) {
                            CameraSelector.LENS_FACING_FRONT
                        } else {
                            CameraSelector.LENS_FACING_BACK
                        }

                    recognizedGesture =
                        "Show your hand to the camera."

                    confidence = ""
                },

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    if (
                        lensFacing ==
                        CameraSelector.LENS_FACING_BACK
                    ) {
                        "Switch to Front Camera"
                    } else {
                        "Switch to Back Camera"
                    }
                )
            }

        } else {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(24.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment =
                        Alignment.CenterHorizontally,
                    verticalArrangement =
                        Arrangement.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Camera permission is needed")

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            permissionLauncher.launch(
                                Manifest.permission.CAMERA
                            )
                        }
                    ) {
                        Text("Enable Camera")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Recognized Gesture",
            fontWeight = FontWeight.Bold,
            fontSize = 19.sp,
            color = DarkText
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = recognizedGesture.replace('_', ' '),
                    color = DarkText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )

                if (confidence.isNotBlank()) {

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Confidence: $confidence",
                        color = SecondaryText,
                        fontSize = 13.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Detects selected general hand gestures, not full ISL.",
            color = SecondaryText,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}


/*
 * CAMERA PREVIEW
*/
@Composable
private fun CameraPreview(
    lensFacing: Int,
    onGestureDetected: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val overlayView = remember {
        HandLandmarkOverlay(context)
    }

    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
            implementationMode =
                PreviewView.ImplementationMode.COMPATIBLE
        }
    }

    DisposableEffect(
        previewView,
        lensFacing,
        lifecycleOwner
    ) {
        val future =
            ProcessCameraProvider.getInstance(context)

        val executor =
            Executors.newSingleThreadExecutor()

        var provider: ProcessCameraProvider? = null
        var gestureHelper: GestureRecognizerHelper? = null
        var disposed = false

        future.addListener({

            try {
                val cameraProvider = future.get()

                if (disposed) {
                    return@addListener
                }

                provider = cameraProvider

                // -----------------------------
                // CAMERA PREVIEW
                // -----------------------------

                val preview =
                    Preview.Builder()
                        .build()
                        .also {
                            it.surfaceProvider =
                                previewView.surfaceProvider
                        }

                // -----------------------------
                // CAMERA SELECTOR
                // -----------------------------

                val selector =
                    CameraSelector.Builder()
                        .requireLensFacing(lensFacing)
                        .build()

                // -----------------------------
                // MEDIAPIPE
                // -----------------------------

                gestureHelper =
                    try {
                        GestureRecognizerHelper(context)
                    } catch (e: Exception) {

                        android.util.Log.e(
                            "UNIVOICE_GESTURE",
                            "Gesture recognizer initialization failed",
                            e
                        )

                        null
                    }

                // -----------------------------
                // IMAGE ANALYSIS
                // -----------------------------

                val imageAnalysis =
                    ImageAnalysis.Builder()
                        .setBackpressureStrategy(
                            ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
                        )
                        .setOutputImageFormat(
                            ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
                        )
                        .build()

                var lastGesture = ""
                var previousNoFingerDistance: Float? = null
                var noMovementDetected = false

                imageAnalysis.setAnalyzer(
                    executor
                ) { imageProxy ->

                    try {

                        val helper = gestureHelper

                        if (
                            helper == null ||
                            disposed
                        ) {
                            imageProxy.close()
                            return@setAnalyzer
                        }

                        // Convert camera frame
                        val bitmap =
                            imageProxy.toRgbaBitmap()

                        // Rotate according to camera
                        val rotatedBitmap =
                            rotateBitmap(
                                bitmap,
                                imageProxy
                                    .imageInfo
                                    .rotationDegrees
                            )

                        // Run MediaPipe
                        val result =
                            helper.recognize(
                                rotatedBitmap
                            )
                        val hands = result.landmarks()
                        var currentNoFingerDistance: Float? = null

                        if (hands.isNotEmpty() && hands.first().size >= 21) {
                            val hand = hands.first()
                            val indexTip = hand[8]
                            val middleTip = hand[12]

                            currentNoFingerDistance = kotlin.math.sqrt(
                                (indexTip.x() - middleTip.x()) *
                                        (indexTip.x() - middleTip.x()) +
                                        (indexTip.y() - middleTip.y()) *
                                        (indexTip.y() - middleTip.y())
                            )
                        }

                        val previousDistance = previousNoFingerDistance
                        noMovementDetected =
                            currentNoFingerDistance != null &&
                                    previousDistance != null &&
                                    previousDistance - currentNoFingerDistance > 0.025f

                        previousNoFingerDistance = currentNoFingerDistance

                        val category =
                            result
                                .gestures()
                                .firstOrNull()
                                ?.firstOrNull()





                        val gestureName =
                            if (isNamasteGesture(hands)) {
                                "NAMASTE"
                            } else if (hands.isNotEmpty() && isPleaseGesture(hands.first())) {
                                "PLEASE"
                            } else if (hands.isNotEmpty() && isLittleGesture(hands.first())) {
                                "LITTLE"
                            } else if (
                                category != null && category.score() >= 0.5f
                            ) {


                                when (category.categoryName()) {
                                    "Open_Palm" -> "Hi"
                                    "Thumb_Up" -> "Pass / OK"
                                    "Thumb_Down" -> "NO"
                                    "Closed_Fist" -> "YES"
                                    "Victory" -> "value / Two"
                                    "ILoveYou" -> "I Love You"
                                    "Pointing_Up" -> "One"
                                    "Pointing_down" -> "Down"
                                    else -> {
                                        if (
                                            category.categoryName().equals("None", ignoreCase = true) ||
                                            category.categoryName().isBlank()
                                        ) {
                                            "Show your hand to the camera."
                                        } else {
                                            category.categoryName()
                                        }
                                    }
                                }
                            } else {
                                "Show your hand to the camera."
                            }

                        val confidence =
                            if (
                                category != null &&
                                category.score() >= 0.5f
                            ) {
                                "%.0f%%".format(
                                    category.score() * 100
                                )
                            } else {
                                ""
                            }

                        // Only update UI when result changes
                        if (
                            gestureName != lastGesture
                        ) {

                            lastGesture = gestureName

                            android.os.Handler(
                                android.os.Looper.getMainLooper()
                            ).post {

                                if (!disposed) {
                                    overlayView.setResults(
                                        result,
                                        rotatedBitmap.width,
                                        rotatedBitmap.height
                                    )

                                    onGestureDetected(
                                        gestureName,
                                        confidence
                                    )
                                    GestureHistory.save(
                                        context,
                                        gestureName
                                    )
                                }
                            }
                        }

                    } catch (e: Exception) {

                        android.util.Log.e(
                            "UNIVOICE_GESTURE",
                            "Frame analysis failed",
                            e
                        )

                    } finally {

                        imageProxy.close()
                    }
                }

                // -----------------------------
                // BIND CAMERA
                // -----------------------------

                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    imageAnalysis
                )

            } catch (e: Exception) {

                android.util.Log.e(
                    "UNIVOICE_CAMERA",
                    "Camera setup failed",
                    e
                )
            }

        }, ContextCompat.getMainExecutor(context))

        // -----------------------------
        // CLEANUP
        // -----------------------------

        onDispose {

            disposed = true

            provider?.unbindAll()

            gestureHelper?.close()

            executor.shutdown()
        }
    }

    Box(
        modifier = modifier
            .background(
                Color.Black,
                RoundedCornerShape(24.dp)
            )
    ) {

        AndroidView(
            factory = {
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        AndroidView(
            factory = {
                overlayView
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}


/*
 * ROTATION HELPER
 *
 * Also kept for the next recognition step.
 */
private fun rotateBitmap(
    bitmap: Bitmap,
    rotationDegrees: Int
): Bitmap {

    if (rotationDegrees == 0) {
        return bitmap
    }

    val matrix =
        Matrix().apply {
            postRotate(
                rotationDegrees.toFloat()
            )
        }

    return Bitmap.createBitmap(
        bitmap,
        0,
        0,
        bitmap.width,
        bitmap.height,
        matrix,
        true
    )
}

@androidx.annotation.OptIn(
    androidx.camera.core.ExperimentalGetImage::class
)
private fun ImageProxy.toRgbaBitmap(): Bitmap {

    val plane = planes[0]
    val buffer = plane.buffer.duplicate()

    val rowStride = plane.rowStride
    val pixelStride = plane.pixelStride

    val pixels = IntArray(width * height)
    val row = ByteArray(rowStride)

    for (y in 0 until height) {

        buffer.position(y * rowStride)

        buffer.get(
            row,
            0,
            minOf(rowStride, buffer.remaining())
        )

        for (x in 0 until width) {

            val offset = x * pixelStride

            if (offset + 3 >= row.size) {
                continue
            }

            val r = row[offset].toInt() and 0xFF
            val g = row[offset + 1].toInt() and 0xFF
            val b = row[offset + 2].toInt() and 0xFF
            val a = row[offset + 3].toInt() and 0xFF

            pixels[y * width + x] =
                (a shl 24) or
                        (r shl 16) or
                        (g shl 8) or
                        b
        }
    }

    return Bitmap.createBitmap(
        pixels,
        width,
        height,
        Bitmap.Config.ARGB_8888
    )
}


private fun isPleaseGesture(
    hand: List<com.google.mediapipe.tasks.components.containers.NormalizedLandmark>
): Boolean {
    if (hand.size < 21) return false

    val tips = listOf(4, 8, 12, 16, 20)
    val points = tips.map { hand[it] }

    val centerX = points.map { it.x() }.average()
    val centerY = points.map { it.y() }.average()

    val spread = points.maxOf { point ->
        kotlin.math.sqrt(
            (point.x() - centerX) * (point.x() - centerX) +
                    (point.y() - centerY) * (point.y() - centerY)
        )
    }

    return spread < 0.07
}

private fun isLittleGesture(
    hand: List<com.google.mediapipe.tasks.components.containers.NormalizedLandmark>
): Boolean {
    if (hand.size < 21) return false

    val thumb = hand[4]
    val index = hand[8]

    val distance = kotlin.math.sqrt(
        (thumb.x() - index.x()) * (thumb.x() - index.x()) +
                (thumb.y() - index.y()) * (thumb.y() - index.y())
    )

    val middleExtended =
        hand[12].y() < hand[10].y()

    return distance in 0.04f..0.12f && middleExtended
}

private fun isNamasteGesture(
    hands: List<List<com.google.mediapipe.tasks.components.containers.NormalizedLandmark>>
): Boolean {
    if (hands.size < 2) return false

    val firstHand = hands[0]
    val secondHand = hands[1]

    if (firstHand.size < 21 || secondHand.size < 21) {
        return false
    }

    // Compare the wrist positions of both hands.
    val wristDistance = kotlin.math.sqrt(
        (firstHand[0].x() - secondHand[0].x()) *
                (firstHand[0].x() - secondHand[0].x()) +
                (firstHand[0].y() - secondHand[0].y()) *
                (firstHand[0].y() - secondHand[0].y())
    )

    // Compare the middle fingertip positions.
    val fingerDistance = kotlin.math.sqrt(
        (firstHand[12].x() - secondHand[12].x()) *
                (firstHand[12].x() - secondHand[12].x()) +
                (firstHand[12].y() - secondHand[12].y()) *
                (firstHand[12].y() - secondHand[12].y())
    )

    return wristDistance < 0.25f && fingerDistance < 0.12f
}


