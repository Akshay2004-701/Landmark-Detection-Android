package com.example.landmarkdetektion.domain

import android.graphics.Bitmap

interface LandmarkClassifier {
    fun classify(bitmap: Bitmap , rotation:Int):List<classification>
}