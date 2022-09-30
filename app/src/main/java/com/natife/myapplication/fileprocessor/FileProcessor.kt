package com.natife.myapplication.fileprocessor

interface FileProcessor {
    fun writeToFile(fileName:String,data: List<String>)
    fun readFromFile(fileName: String): List<String>
}