package com.krucha.kotlinsample.screen.detail.film.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.krucha.kotlinsample.data.model.Film

abstract class DetailLiveData<T>(private val film: LiveData<Film>) : LiveData<T>() {

    abstract fun fromFilm(film: Film): T

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)

        film.observe(owner, Observer {
            val film = it ?: return@Observer
            value = fromFilm(film)
        })
    }
}