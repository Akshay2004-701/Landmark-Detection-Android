package com.example.landmarkdetektion.data

import android.graphics.Bitmap
import android.health.connect.datatypes.HeightRecord
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.landmarkdetektion.domain.LandmarkClassifier
import com.example.landmarkdetektion.domain.classification

class LandmarkImageAnalyzer(
    private val classifier: LandmarkClassifier,
    private val onResults:(List<classification>) -> Unit
):ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if ((frameSkipCounter % 60) == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image.toBitmap()
                .centerCrop(321, 321)
            val results = classifier.classify(bitmap, rotationDegrees)
            onResults(results)
        }
        frameSkipCounter ++
        image.close()
    }
}

fun Bitmap.centerCrop(desiredHeight:Int , desiredWidth:Int):Bitmap{
    val xStart = (width - desiredWidth) / 2
    val yStart = (height - desiredHeight) / 2
    if (xStart<0 || yStart<0 || desiredHeight>height || desiredWidth>width){
        throw IllegalArgumentException("Invalid arguments for the expression")
    }
    return Bitmap.createBitmap(this,xStart,yStart,desiredWidth,desiredHeight)
}