package com.natife.myapplication

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.natife.myapplication.ui.screens.main.MainScreen
import com.natife.myapplication.ui.screens.main.MainViewModel
import com.natife.myapplication.ui.screens.second.SecondScreen
import com.natife.myapplication.ui.screens.second.SecondViewModel
import com.natife.myapplication.utils.Routes
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppNavigator(mainViewModel: MainViewModel,secondViewModel: SecondViewModel) {
    val navController = rememberNavController()
//    val mainViewModel = getViewModel<MainViewModel>()
//    val secondViewModel = getViewModel<SecondViewModel>()
    NavHost(navController = navController, startDestination = Routes.MainScreenRoute.route) {
        composable(Routes.MainScreenRoute.route) {
            MainScreen(
                mainViewModel.gifQuery,
                mainViewModel.gifsFlowUiState,
                mainViewModel.savedGifs,
                mainViewModel::onGifQueryChanged,
                mainViewModel::deleteGif,
                navigateToSecondScreen = { gif->
                    secondViewModel.gif = gif
                    navController.navigate(Routes.SecondScreenRoute.route)
                },
                navigateToSecondScreenFromSaved = { savedGif ->
                    secondViewModel.savedGif = savedGif
                    navController.navigate(Routes.SecondScreenRoute.route)
                }
            )
        }
        composable(Routes.SecondScreenRoute.route) {
            SecondScreen(gif = secondViewModel.gif, savedGifs = mainViewModel.savedGifs)
        }
    }
}