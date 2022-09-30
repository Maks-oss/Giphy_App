package com.natife.myapplication.ui.screens.main

import android.os.Build.VERSION.SDK_INT
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

private val testGifs = List(10) {
    "https://media3.giphy.com/media/xT9Iglzjt6lXG1QJsk/giphy.gif?cid=8bfddc46ml1gbvqv525j1tni1ku7tpsy5sqpmsndttx3o7zo&rid=giphy.gif&ct=g"
}

@Composable
fun MainScreen(gifQuery:String,onTextQueryChanged:(String)->Unit) {

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            value = gifQuery,
            onValueChange = onTextQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = "Enter gif...") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        LazyColumn {
            items(testGifs) {
                GifListItem(it)
            }
        }

    }
}

@Composable
private fun GifListItem(it: String) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    val imagePainter = rememberAsyncImagePainter(
        imageLoader = imageLoader, model = ImageRequest.Builder(context)
            .data(it)
            .crossfade(true)
            .build()
    )

    Image(
        painter = imagePainter,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().height(200.dp)
    )

    Spacer(modifier = Modifier.padding(8.dp))
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreenPreview() {
//    MainScreen()
}