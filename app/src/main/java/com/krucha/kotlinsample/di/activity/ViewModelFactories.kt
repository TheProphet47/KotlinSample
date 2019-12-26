package com.krucha.kotlinsample.di.activity

import com.krucha.kotlinsample.screen.SimpleViewModelFactory
import com.krucha.kotlinsample.screen.auth.login.LoginViewModel
import com.krucha.kotlinsample.screen.auth.register.RegisterViewModel
import com.krucha.kotlinsample.screen.detail.film.viewmodel.DetailFilmViewModel

interface ViewModelFactories {
    fun loginViewModelFactory(): SimpleViewModelFactory<LoginViewModel>
    fun registerViewModelFactory(): SimpleViewModelFactory<RegisterViewModel>
    fun detailFilmViewModelFactory(): SimpleViewModelFactory<DetailFilmViewModel>
}