package com.natife.myapplication.ui.screens.main

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
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
import com.natife.myapplication.R
import com.natife.myapplication.room.Gif
import com.natife.myapplication.ui.animations.DisplayShimmer
import com.natife.myapplication.ui.composeutils.LoadGif
import com.natife.myapplication.ui.composeutils.SwipeToDelete
import com.natife.myapplication.ui.uistate.UiState
import com.natife.myapplication.utils.SavedGif
import kotlinx.coroutines.flow.Flow

@ExperimentalFoundationApi
@Composable
fun MainScreen(
    gifQuery: String,
    gifsFlowUiState: UiState<Flow<PagingData<Gif>>>,
    savedGifs: List<SavedGif>,
    onTextQueryChanged: (String) -> Unit,
    swipeToDelete: (String) -> Unit,
    navigateToSecondScreen:(Gif)->Unit,
    navigateToSecondScreenFromSaved:(SavedGif)->Unit
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
        if (lazyPagingItems?.loadState?.refresh is LoadState.Error) {
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.empty_response),
                Toast.LENGTH_SHORT
            ).show()
        }
        DisplayShimmer(isLoading = gifsFlowUiState.isLoading)
        if (lazyPagingItems == null) {
            DisplaySavedGifsList(savedGifs, swipeToDelete = swipeToDelete, navigateToSecondScreen = navigateToSecondScreenFromSaved)
        } else {
            DisplayGifsList(lazyPagingItems, navigateToSecondScreen = navigateToSecondScreen)
        }
    }
}

@Composable
private fun DisplayGifsList(
    lazyPagingItems: LazyPagingItems<Gif>?,
    navigateToSecondScreen:(Gif)->Unit
) {
    lazyPagingItems?.let { gif ->
        LazyColumn {
            items(gif) {
                GifListItem(it!!, navigateToSecondScreen)
            }
        }
    }
}

@Composable
private fun DisplaySavedGifsList(
    savedGifs: List<SavedGif>,
    swipeToDelete: (String) -> Unit,
    navigateToSecondScreen: (SavedGif) -> Unit
) {

    LazyColumn {
        items(savedGifs, key = { it.id }) {
            SavedGifListItem(swipeToDelete, it, navigateToSecondScreen)
        }

    }

}

@Composable
private fun SavedGifListItem(
    swipeToDelete: (String) -> Unit,
    gif: SavedGif,
    navigateToSecondScreen: (SavedGif) -> Unit
) {
    SwipeToDelete(delete = {
        swipeToDelete(gif.id)
    }) {
        Column(modifier = Modifier.clickable { navigateToSecondScreen(gif) }) {
            Image(
                bitmap = gif.imageBitmap.asImageBitmap(),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun GifListItem(gif: Gif,navigateToSecondScreen: (Gif) -> Unit) {
    LoadGif(
        gif, modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToSecondScreen(gif) }
            .height(200.dp)
    )

    Spacer(modifier = Modifier.padding(8.dp))


}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MainScreenPreview() {
//    MainScreen()
}