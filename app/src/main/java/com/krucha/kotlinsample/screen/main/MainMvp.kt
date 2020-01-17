package com.krucha.kotlinsample.screen.main

import android.os.Bundle
import com.krucha.kotlinsample.data.model.Film

interface MainMvp {

    interface View {
        fun setFilms(films: List<Film>)
        fun showFilmDetails(bundle: Bundle)
    }

    interface Presenter {
        var view: View?

        fun subscribe()
        fun unsubscribe()

        fun logout()
        fun onAddFabClick()
        fun onFilmItemClick(filmId: Long)
    }
}