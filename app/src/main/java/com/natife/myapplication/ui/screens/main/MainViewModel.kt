package com.natife.myapplication.ui.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natife.myapplication.R
import com.natife.myapplication.repository.GifRepository
import com.natife.myapplication.ui.uistate.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException

class MainViewModel(private val gifRepository: GifRepository) : ViewModel() {
    var gifQuery by mutableStateOf("")
        private set
    private var job: Job? = null
    var gifUiState by mutableStateOf(UiState<String>())
    fun onGifQueryChanged(value: String) {
        job?.cancel()
        if (value.isNotEmpty()) {
            job = viewModelScope.launch {
                gifQuery = value
                gifUiState = gifUiState.copy(errorMessage = null)
                delay(2000)
                fetchGifs()
            }
        }
    }

    private fun fetchGifs() {
        viewModelScope.launch {
            gifUiState = gifUiState.copy(data = null, isLoading = true, errorMessage = null)
            gifUiState = try {
                val gifs = gifRepository.fetchGifs(gifQuery)
                delay(1000)
                if (gifs.isEmpty()) {
                    gifUiState.copy(data = null, false, R.string.empty_response)
                } else {
                    gifUiState.copy(data = gifs, false, null)
                }
            } catch (exc: TimeoutException) {
                gifUiState.copy(data = null, false, R.string.netowrk_error)

            }

        }
    }
}