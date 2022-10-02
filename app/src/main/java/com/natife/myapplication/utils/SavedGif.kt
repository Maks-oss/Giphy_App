package com.natife.myapplication.utils

import android.graphics.Bitmap
import com.natife.myapplication.room.Gif
import android.provider.MediaStore
import java.io.ByteArrayOutputStream


data class SavedGif(
    val id: String,
    val gifByteArray: ByteArray
)