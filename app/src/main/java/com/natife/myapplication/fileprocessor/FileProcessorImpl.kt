package com.natife.myapplication.fileprocessor

import android.content.Context
import android.graphics.BitmapFactory
import com.natife.myapplication.utils.SavedGif
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL


class FileProcessorImpl(private val context: Context) : FileProcessor {
    override suspend fun writeToFile(fileName: String, data: String): Unit =
        withContext(Dispatchers.IO) {
            val url = URL(data).readBytes()
            val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutputStream.write(url)
        }


    override suspend fun readFromFile(fileNames: List<String>): List<SavedGif> {
        return fileNames.map { gifId ->
            val path = context.filesDir.path
            SavedGif(id = gifId, gifByteArray = File("$path/$gifId.gif").readBytes())
        }

    }

    override suspend fun deleteFile(fileName: String): Boolean {
        val path = context.filesDir.path
        return File("$path/$fileName.gif").delete()
    }
}