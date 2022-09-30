package com.natife.myapplication.ui.uistate

data class UiState<T>(
    val data: List<T>? = null,
    val isLoading: Boolean = false,
    val errorMessage:Int? = null
)