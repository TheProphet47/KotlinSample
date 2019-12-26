package com.krucha.kotlinsample.di

import android.app.Activity
import com.krucha.kotlinsample.di.app.AppComponent

interface DaggerComponentProvider {
    val appComponent: AppComponent
}

val Activity.injector get() =
    (application as DaggerComponentProvider).appComponent.activityComponentFactory().create()


