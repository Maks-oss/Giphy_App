package com.natife.myapplication.ui.composeutils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDelete(delete: () -> Unit, content: @Composable () -> Unit) {
    val swipeDismissState = rememberDismissState(confirmStateChange = {
        if (it == DismissValue.DismissedToEnd){
            delete()
        }
        true
    })
    SwipeToDismiss(
        state = swipeDismissState,
        directions = setOf(DismissDirection.StartToEnd),
        background = {
        }) {
        content()
    }
}