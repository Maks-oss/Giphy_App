package com.natife.myapplication.ui.screens.second

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.natife.myapplication.room.Gif
import com.natife.myapplication.ui.composeutils.LoadGif
import com.natife.myapplication.utils.Constants
import com.natife.myapplication.utils.SavedGif

@Composable
fun SecondScreen(gif: Gif?, savedGif: SavedGif?, navigatedFrom: String, savedGifs: List<SavedGif>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (navigatedFrom == Constants.SAVED && savedGif != null) {
            Image(
                bitmap = savedGif.imageBitmap.asImageBitmap(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        } else if (navigatedFrom == Constants.SERVER && gif != null) {
            LoadGif(
                gif = gif, modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp)
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        DisplayHorizontalList(savedGifs)
    }

}


@Composable
private fun DisplayHorizontalList(savedGifs: List<SavedGif>) {
    LazyRow(modifier = Modifier.padding(8.dp)) {
        items(savedGifs) {
            Row {
                Image(
                    bitmap = it.imageBitmap.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )

                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}