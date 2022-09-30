package com.natife.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.natife.myapplication.ui.screens.main.MainScreen
import com.natife.myapplication.ui.screens.main.MainViewModel
import com.natife.myapplication.ui.theme.GiphyAppTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel = getViewModel<MainViewModel>()
        setContent {
            GiphyAppTheme {
               MainScreen(mainViewModel.gifQuery,mainViewModel::onGifQueryChanged)
            }
        }
    }
}
