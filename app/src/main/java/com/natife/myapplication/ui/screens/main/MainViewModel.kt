package com.natife.myapplication.ui.screens.main

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.natife.myapplication.repository.GifRepository
import com.natife.myapplication.room.Gif
import com.natife.myapplication.ui.uistate.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okio.FileNotFoundException

class MainViewModel(private val gifRepository: GifRepository) : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    var gifQuery by mutableStateOf("")
        private set
    private var job: Job? = null

    var gifsFlowUiState by mutableStateOf(UiState<Flow<PagingData<Gif>>>())
        private set
    var savedGifs by mutableStateOf<List<Pair<String,Bitmap>>>(emptyList())
        private set

    init {
        fetchSavedGifs()
    }

    fun onGifQueryChanged(value: String) {
        job?.cancel()
        if (value.isNotEmpty()) {
            job = viewModelScope.launch {
                gifQuery = value
                delay(2000)
                fetchGifs()
            }
        }
    }

    private suspend fun fetchGifs() {
        gifsFlowUiState = gifsFlowUiState.copy(data = null, isLoading = true, errorMessage = null)
        delay(1000)
        val gifsFlow = gifRepository.fetchGifs(gifQuery).cachedIn(viewModelScope)

        gifsFlowUiState =
            gifsFlowUiState.copy(data = gifsFlow, isLoading = false, errorMessage = null)

    }

    private fun fetchSavedGifs() {
        viewModelScope.launch {
            savedGifs = try {
                gifRepository.fetchGifsFromLocalStorage()
            } catch (ex: FileNotFoundException) {
                Log.e(TAG, "fetchSavedGifs: ${ex.message}")
                emptyList()
            }
        }
    }

    fun deleteGif(gif: String) {
        viewModelScope.launch {
            gifRepository.deleteGifFromLocalStorage(gif)
            savedGifs = savedGifs.filter { it.first != gif }
        }
    }


}

