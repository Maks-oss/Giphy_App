package com.natife.myapplication.utils

import com.natife.myapplication.data.dto.GifDto

object GifsMapper {
    fun convertGifDtoToGifList(gifDto: GifDto):List<String>{
        return gifDto.data.map { data -> data.images.original.url }
    }
}