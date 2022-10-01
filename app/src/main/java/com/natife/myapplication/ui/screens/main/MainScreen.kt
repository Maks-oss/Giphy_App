package com.natife.myapplication.ui.screens.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build.VERSION.SDK_INT
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.natife.myapplication.ui.animations.DisplayShimmer
import com.natife.myapplication.ui.uistate.UiState
import kotlinx.coroutines.flow.Flow
import com.natife.myapplication.R

@ExperimentalFoundationApi
@Composable
fun MainScreen(
    gifQuery: String,
    gifsFlowUiState: UiState<Flow<PagingData<String>>>,
    savedGifs: List<Bitmap>,
    onTextQueryChanged: (String) -> Unit,
) {
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

        val lazyPagingItems = gifsFlowUiState.data?.collectAsLazyPagingItems()
        if (lazyPagingItems?.loadState?.refresh is LoadState.Error){
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.empty_response),
                Toast.LENGTH_SHORT
            ).show()
        }
        DisplayShimmer(isLoading = gifsFlowUiState.isLoading)
        if (lazyPagingItems == null){
            DisplaySavedGifsList(savedGifs)
        } else {
            DisplayGifsList(lazyPagingItems = lazyPagingItems)
        }
    }
}

@Composable
private fun DisplayGifsList(lazyPagingItems: LazyPagingItems<String>?) {
    lazyPagingItems?.let { gif ->
        LazyColumn {
            items(gif) {
                GifListItem(it!!)
            }
        }
    }
}
@Composable
private fun DisplaySavedGifsList(savedGifs: List<Bitmap>) {
    LazyColumn{
        items(savedGifs) {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
private fun DisplayGifsList(gifsUiState: UiState<Flow<PagingData<String>>>) {
    gifsUiState.data?.let { gifsFlow ->
        val lazyPagingItems = gifsFlow.collectAsLazyPagingItems()
        LazyColumn {
            items(lazyPagingItems){
                GifListItem(it!!)
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
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )

    Spacer(modifier = Modifier.padding(8.dp))
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreenPreview() {
//    MainScreen()
}