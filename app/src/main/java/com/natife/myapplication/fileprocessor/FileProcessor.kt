package com.natife.myapplication.fileprocessor

import android.graphics.Bitmap

interface FileProcessor {
    suspend fun writeToFile(fileName:String,data: String)
    suspend fun readFromFile(fileNames: List<String>): List<Bitmap>
}