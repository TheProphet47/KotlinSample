package com.krucha.kotlinsample.screen.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krucha.kotlinsample.data.repository.FilmRepository
import com.krucha.kotlinsample.data.room.SimpleDatabase
import com.krucha.kotlinsample.features.auth.LoginRepository
import com.krucha.kotlinsample.screen.detail.film.viewmodel.DetailFilmViewModel
import java.lang.Exception

class DetailViewModelFactory(app: Application) : ViewModelProvider.AndroidViewModelFactory(app) {

    var filmId: Long = 0

    private val database = SimpleDatabase.getInstance(app)
    private val filmRepository = FilmRepository(database.filmDao())

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            DetailFilmViewModel::class.java -> {
                DetailLog.debug("Create FilmDetailViewModel")
                val user = LoginRepository.getInstance().user ?: throw Exception("User didn't log in")

                DetailFilmViewModel(filmRepository, filmId, user) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}