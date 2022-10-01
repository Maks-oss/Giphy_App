package com.natife.myapplication.ui.screens.second

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.natife.myapplication.repository.GifRepository
import com.natife.myapplication.room.Gif
import com.natife.myapplication.utils.SavedGif

class SecondViewModel: ViewModel() {
    var gif by mutableStateOf<Gif?>(null)
    var savedGif by mutableStateOf<SavedGif?>(null)
}