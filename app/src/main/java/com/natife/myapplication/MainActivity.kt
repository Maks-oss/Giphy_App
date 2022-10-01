package com.natife.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.natife.myapplication.ui.screens.main.MainScreen
import com.natife.myapplication.ui.screens.main.MainViewModel
import com.natife.myapplication.ui.theme.GiphyAppTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = getViewModel<MainViewModel>()
        setContent {
            GiphyAppTheme {
                MainScreen(
                    mainViewModel.gifQuery,
                    mainViewModel.gifsFlowUiState,
                    mainViewModel.savedGifs,
                    mainViewModel::onGifQueryChanged,
                )
            }
        }
    }
}
