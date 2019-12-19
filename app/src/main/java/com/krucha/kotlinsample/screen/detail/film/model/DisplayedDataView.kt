package com.krucha.kotlinsample.screen.detail.film.model

import android.net.Uri
import com.krucha.kotlinsample.data.model.Film

data class DisplayedDataView (
    val name: String? = null,
    val year: Int? = null,
    val genre: String? = null,
    val description: String? = null,
    val imageUri: Uri? = null
) {
    companion object {
        fun fromFilm(film: Film) =
            DisplayedDataView(
                name = film.name,
                year = film.year,
                description = film.description,
                genre = film.genres?.joinToString(", "),
                imageUri = film.imagePath?.let { Uri.parse(it) }
            )
    }
}