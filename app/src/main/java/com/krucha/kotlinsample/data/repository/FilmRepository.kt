package com.krucha.kotlinsample.data.repository

import androidx.lifecycle.LiveData
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.data.model.FilmWithOwner
import com.krucha.kotlinsample.data.room.FilmDao

class FilmRepository(private val dao : FilmDao) {
    val films = dao.getAllFilm()

    fun getFilmWithOwner(id: Long) : LiveData<FilmWithOwner> {
        return dao.getFilmWithOwner(id)
    }

    suspend fun insert(film: Film) {
         dao.insert(film)
    }

    suspend fun update(film: Film) {
        dao.update(film)
    }

    suspend fun delete(film: Film) {
        dao.delete(film)
    }
}