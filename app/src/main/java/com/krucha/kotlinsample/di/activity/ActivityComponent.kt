package com.krucha.kotlinsample.di.activity

import com.krucha.kotlinsample.di.BaseComponent
import com.krucha.kotlinsample.di.SubcomponentFactory
import com.krucha.kotlinsample.di.scope.PerActivity
import com.krucha.kotlinsample.screen.main.MainActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [MainActivityModule::class])
interface ActivityComponent : BaseComponent, ViewModelFactories {

    @Subcomponent.Factory
    interface Factory : SubcomponentFactory<ActivityComponent> {
        fun create(): ActivityComponent
    }

    fun inject(activity: MainActivity)

}