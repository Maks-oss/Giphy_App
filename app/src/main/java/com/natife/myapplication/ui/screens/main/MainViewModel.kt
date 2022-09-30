package com.natife.myapplication.ui.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.natife.myapplication.R
import com.natife.myapplication.repository.GifRepository
import com.natife.myapplication.ui.uistate.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException

class MainViewModel(private val gifRepository: GifRepository) : ViewModel() {
    var gifQuery by mutableStateOf("")
        private set
    private var job: Job? = null
    var gifUiState = UiState<Flow<PagingData<String>>>()
    var isLoading: Boolean by mutableStateOf(false)
    var gifsFlow: Flow<PagingData<String>>? = null

    fun onGifQueryChanged(value: String) {
        job?.cancel()
        if (value.isNotEmpty()) {
            job = viewModelScope.launch {
                gifQuery = value
                delay(2000)
                isLoading = true
                delay(1000)
                fetchGifs()
                isLoading = false
            }
        }
    }

//    private fun fetchGifs() {
////        viewModelScope.launch {
//            gifUiState = gifUiState.copy(data = null, isLoading = true, errorMessage = null)
//            gifUiState = try {
//                val gifs = gifRepository.fetchGifs(gifQuery).cachedIn(viewModelScope)
////                delay(1000)
//                gifUiState.copy(data = gifs, false, null)
//            } catch (exc: TimeoutException) {
//                gifUiState.copy(data = null, false, R.string.netowrk_error)
//
//            }
//
////        }
//    }

    private fun fetchGifs() {
        gifsFlow = gifRepository.fetchGifs(gifQuery).cachedIn(viewModelScope)
//        return gifRepository.fetchGifs(gifQuery).cachedIn(viewModelScope)
    }

}