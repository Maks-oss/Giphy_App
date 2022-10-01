package com.natife.myapplication

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.natife.myapplication.ui.screens.main.MainScreen
import com.natife.myapplication.ui.screens.main.MainViewModel
import com.natife.myapplication.ui.screens.second.SecondScreen
import com.natife.myapplication.ui.screens.second.SecondViewModel
import com.natife.myapplication.utils.Constants
import com.natife.myapplication.utils.Routes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppNavigator(mainViewModel: MainViewModel, secondViewModel: SecondViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MainScreenRoute.route) {
        composable(Routes.MainScreenRoute.route) {
            MainScreen(
                mainViewModel.gifQuery,
                mainViewModel.gifsFlowUiState,
                mainViewModel.savedGifs,
                mainViewModel::onGifQueryChanged,
                mainViewModel::deleteGif,
                navigateToSecondScreen = { gif ->
                    secondViewModel.gif = gif
                    navController.navigate(Routes.SecondScreenRoute.navigateWithArgument(Constants.SERVER))
                },
                navigateToSecondScreenFromSaved = { savedGif ->
                    secondViewModel.savedGif = savedGif
                    navController.navigate(Routes.SecondScreenRoute.navigateWithArgument(Constants.SAVED))
                }

            )
        }
        composable(
            Routes.SecondScreenRoute.route,
            arguments = listOf(navArgument("navigatedFrom") {
                type = NavType.StringType
            })) { backStackEntry ->

                SecondScreen(
                    gif = secondViewModel.gif,
                    savedGifs = mainViewModel.savedGifs,
                    savedGif = secondViewModel.savedGif,
                    navigatedFrom = backStackEntry.arguments!!.getString("navigatedFrom","")
                )
            }
    }
}