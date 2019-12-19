package com.krucha.kotlinsample.screen.detail.film.model

import android.net.Uri
import com.krucha.kotlinsample.data.model.Film

data class DataForFilm (
    val name: String? = null,
    val year: Int? = null,
    val genre: List<String>? = null,
    val description: String? = null
) {
    fun toFilm(id: Long, userId: Long?, imageUri: Uri?): Film {
        return Film(id = id, userId = userId, imagePath = imageUri?.toString(),
            name = name, year = year, genres = genre, description = description)
    }
}