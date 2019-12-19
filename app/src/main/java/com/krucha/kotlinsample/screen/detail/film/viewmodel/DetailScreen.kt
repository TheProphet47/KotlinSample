package com.krucha.kotlinsample.screen.detail.film.viewmodel

import java.io.Serializable

sealed class DetailScreen(val isFab: Boolean): Serializable {

    class Edit : DetailScreen(true)
    class View : DetailScreen(false)
}

