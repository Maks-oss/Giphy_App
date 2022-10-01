package com.natife.myapplication.utils

import com.natife.myapplication.data.dto.GifDto
import com.natife.myapplication.room.Gif

object GifsMapper {
    fun convertGifDtoToGifList(gifDto: GifDto):List<Gif>{
        return gifDto.data.map { data -> Gif(id = data.id,url = data.images.original.url) }
    }
}