package com.krucha.kotlinsample.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.data.model.FilmWithOwner

@Dao
interface FilmDao {

    @Query("SELECT * FROM ${Film.Table.NAME}")
    fun getAllFilm() : LiveData<List<Film>>

    @Query("SELECT * FROM ${Film.Table.NAME} WHERE ${Film.Field.ID} = :id")
    fun getFilmWithOwner(id: Long) : LiveData<FilmWithOwner>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(film: Film)
    @Update
    suspend fun update(film: Film)
    @Delete
    suspend fun delete(film: Film)
}