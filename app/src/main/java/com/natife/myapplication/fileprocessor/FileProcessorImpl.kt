package com.natife.myapplication.fileprocessor

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Movie
import android.graphics.drawable.AnimatedImageDrawable
import android.util.Log
import com.natife.myapplication.utils.AnimatedGifEncoder
import com.natife.myapplication.utils.SavedGif
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URL


class FileProcessorImpl(private val context: Context) : FileProcessor {
    override suspend fun writeToFile(fileName: String, data: String): Unit =
        withContext(Dispatchers.IO) {
            val url = URL(data)
            val bitmap = url.openConnection().getInputStream()
            val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutputStream.write(bitmap.readBytes())
//            AnimatedGifEncoder().
        }


    override suspend fun readFromFile(fileNames: List<String>): List<SavedGif> {
        return fileNames.map { gifId ->
            val path = context.filesDir.path
            gifId to BitmapFactory.decodeFile("$path/$gifId.gif")
            SavedGif(id = gifId, imageBitmap = BitmapFactory.decodeFile("$path/$gifId.gif"))
        }

    }

    override suspend fun deleteFile(fileName: String): Boolean {
        val path = context.filesDir.path
        return File("$path/$fileName.gif").delete()
    }
}