package com.natife.myapplication.ui.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.natife.myapplication.repository.GifRepository

class MainViewModel(private val gifRepository: GifRepository): ViewModel() {
    var gifQuery by mutableStateOf("")
        private set
    fun onGifQueryChanged(value:String){
        gifQuery = value
    }
}