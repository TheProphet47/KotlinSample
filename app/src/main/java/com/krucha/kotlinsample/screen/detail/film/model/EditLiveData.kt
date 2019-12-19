package com.krucha.kotlinsample.screen.detail.film.model

import android.net.Uri
import androidx.lifecycle.LiveData
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.screen.detail.film.viewmodel.DetailLiveData

class EditLiveData(film: LiveData<Film>) : DetailLiveData<DisplayedDataEdit>(film) {

    override fun fromFilm(film: Film): DisplayedDataEdit {
        return DisplayedDataEdit(
            name = film.name,
            year = film.year,
            description = film.description,
            currentGenre = film.genres,
            imageUri = film.imagePath?.let { Uri.parse(it) }
        )
    }
}