package com.krucha.kotlinsample.di.app

import com.google.firebase.auth.FirebaseAuth
import com.krucha.kotlinsample.di.scope.PerApp
import com.krucha.kotlinsample.features.auth.ILogin
import com.krucha.kotlinsample.features.auth.IRegister
import com.krucha.kotlinsample.features.auth.firebase.FirebaseSource
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [AuthModule.FirebaseSdk::class])
interface AuthModule {

    @Binds @PerApp
    fun provideLoginService(firebase: FirebaseSource): ILogin

    @Binds @PerApp
    fun provideRegisterService(firebase: FirebaseSource): IRegister

    @Module
    object FirebaseSdk {

        @Provides @PerApp
        fun provideFirebase() = FirebaseAuth.getInstance()
    }

}