package com.natife.myapplication.ui.uistate

data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
)