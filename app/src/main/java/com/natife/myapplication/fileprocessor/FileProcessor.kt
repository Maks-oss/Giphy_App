package com.natife.myapplication.fileprocessor

import android.graphics.Bitmap
import com.natife.myapplication.utils.SavedGif

interface FileProcessor {
    suspend fun writeToFile(fileName:String,data: String)
    suspend fun readFromFile(fileNames: List<String>): List<SavedGif>
    suspend fun deleteFile(fileName: String): Boolean
}