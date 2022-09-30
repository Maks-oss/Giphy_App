package com.natife.myapplication.fileprocessor

import android.content.Context
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class FileProcessorImpl(private val context: Context) : FileProcessor {
    override fun writeToFile(fileName: String,data: List<String>) {
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            data.forEach {
                fileOutputStream.write("$it\n".toByteArray())
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun readFromFile(fileName: String): List<String> {
        val fileInputStream: FileInputStream? = context.openFileInput(fileName)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder = mutableListOf<String>()
        var text: String
        while (run {
                text = bufferedReader.readLine()
                text
            } != "") {
            stringBuilder.add(text)
        }
        return stringBuilder.toList()
    }
}