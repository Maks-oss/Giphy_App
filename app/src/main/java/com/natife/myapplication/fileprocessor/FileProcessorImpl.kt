package com.natife.myapplication.fileprocessor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL


class FileProcessorImpl(private val context: Context) : FileProcessor {
    override suspend fun writeToFile(fileName: String, data: String) {
        withContext(Dispatchers.IO) {
            val url = URL(data)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        }
    }

    override suspend fun readFromFile(fileNames: List<String>): List<Bitmap> {
        return fileNames.map {
            val path = context.filesDir.path
            BitmapFactory.decodeFile("$path/$it.gif")
        }

    }
}