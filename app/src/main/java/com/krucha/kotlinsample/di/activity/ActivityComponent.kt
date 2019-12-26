package com.krucha.kotlinsample.di.activity

import com.krucha.kotlinsample.di.BaseComponent
import com.krucha.kotlinsample.di.SubcomponentFactory
import com.krucha.kotlinsample.di.scope.PerActivity
import com.krucha.kotlinsample.screen.main.MainActivity
import dagger.Subcomponent

@PerActivity
@Subcomponent(modules = [])
interface ActivityComponent : BaseComponent, ViewModelFactories {

    @Subcomponent.Factory
    interface Factory : SubcomponentFactory<ActivityComponent> {
        fun create(): ActivityComponent
    }

    fun inject(activity: MainActivity)

}


//    fun viewModelFactories(): Map < Class<*>, SimpleViewModelFactory<out ViewModel> >
//
//    @Binds @IntoMap
//    @ClassKey(LoginActivity::class)
//    fun loginViewModelFactory(impl: SimpleViewModelFactory<LoginViewModel>): SimpleViewModelFactory<out ViewModel>
//
//    @Binds @IntoMap
//    @ClassKey(RegisterActivity::class)
//    fun registerViewModelFactory(impl: SimpleViewModelFactory<RegisterViewModel>): SimpleViewModelFactory<out ViewModel>
//
//    @Binds @IntoMap
//    @ClassKey(DetailFilmActivity::class)
//    fun detailFilmViewModelFactory(impl: SimpleViewModelFactory<DetailFilmViewModel>): SimpleViewModelFactory<out ViewModel>