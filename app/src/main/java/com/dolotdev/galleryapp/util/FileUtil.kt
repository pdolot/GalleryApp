package com.dolotdev.galleryapp.util

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object FileUtil {
    
    fun createImageFile(context: Context?): File? {
        if (context == null) return null
        val folder = File(context.filesDir, "photos")
        folder.mkdirs()
        return File(folder, generatePhotoFileName())
    }

    private fun generatePhotoFileName(): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return "IMG_$timeStamp.jpg"
    }
}