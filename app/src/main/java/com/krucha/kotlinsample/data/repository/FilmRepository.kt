package com.krucha.kotlinsample.data.repository

import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.data.model.FilmWithOwner
import com.krucha.kotlinsample.data.room.FilmDao
import javax.inject.Inject

class FilmRepository @Inject constructor(private val dao : FilmDao) {
    val films = dao.getAllFilm()

    suspend fun getFilm(id: Long): Film {
        return dao.getFilm(id)
    }

    suspend fun getFilmWithOwner(id: Long): FilmWithOwner {
        return dao.getFilmWithOwner(id)
    }

    suspend fun insert(film: Film): Long {
         return dao.insert(film)
    }

    suspend fun update(film: Film) {
        dao.update(film)
    }

    suspend fun delete(film: Film?) {
        film?.let { dao.delete(film) }
    }
}