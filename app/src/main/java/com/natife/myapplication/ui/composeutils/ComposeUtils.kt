package com.natife.myapplication.ui.composeutils

import android.content.Context
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.natife.myapplication.room.Gif

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

@Composable
fun LoadGif(gif: Gif, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    val imagePainter = rememberAsyncImagePainter(
        imageLoader = imageLoader, model = ImageRequest.Builder(context)
            .data(gif.url)
            .crossfade(true)
            .build()
    )

    Image(
        painter = imagePainter,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = /*Modifier
            .fillMaxWidth()
            .height(200.dp)*/
        modifier
    )
}