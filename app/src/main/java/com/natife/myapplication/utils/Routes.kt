package com.natife.myapplication.utils

sealed class Routes(val route: String) {
    object MainScreenRoute: Routes("MainScreen")
    object SecondScreenRoute: Routes("SecondScreen")
}