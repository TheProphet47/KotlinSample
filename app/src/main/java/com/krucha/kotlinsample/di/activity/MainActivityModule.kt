package com.krucha.kotlinsample.di.activity

import com.krucha.kotlinsample.di.scope.PerActivity
import com.krucha.kotlinsample.screen.main.MainMvp
import com.krucha.kotlinsample.screen.main.MainPresenter
import dagger.Binds
import dagger.Module

@Module
interface MainActivityModule {

    @Binds @PerActivity
    fun provideMainPresenter(presenter: MainPresenter): MainMvp.Presenter
}