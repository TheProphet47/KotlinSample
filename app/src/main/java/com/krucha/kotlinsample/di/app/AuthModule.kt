package com.krucha.kotlinsample.di.app

import com.krucha.kotlinsample.di.scope.PerApp
import com.krucha.kotlinsample.features.auth.ILogin
import com.krucha.kotlinsample.features.auth.IRegister
import com.krucha.kotlinsample.features.auth.firebase.FirebaseAuth
import dagger.Binds
import dagger.Module

@Module
interface AuthModule {

    // TODO: проверить один и тотже будет экз или нет, если нет, то добавить скоуп в фиребас
    @Binds @PerApp
    fun provideLoginService(firebase: FirebaseAuth): ILogin

    @Binds @PerApp
    fun provideRegisterService(firebase: FirebaseAuth): IRegister
}