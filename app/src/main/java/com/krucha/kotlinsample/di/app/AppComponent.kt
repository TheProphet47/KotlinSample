package com.krucha.kotlinsample.di.app

import android.content.Context
import com.krucha.kotlinsample.di.BaseComponent
import com.krucha.kotlinsample.di.SubcomponentFactory
import com.krucha.kotlinsample.di.activity.ActivityComponent
import com.krucha.kotlinsample.di.scope.PerApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Provider

@PerApp
@Component(modules = [
    SubcomponentModule::class,
    DatabaseModule::class,
    AuthModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun activityComponentFactory(): ActivityComponent.Factory

//    fun subcomponentBuilders(): Map < Class<*>?, Provider<SubcomponentFactory<out BaseComponent>> >
}