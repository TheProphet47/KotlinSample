package com.krucha.kotlinsample

import android.app.Application
import com.krucha.kotlinsample.di.DaggerComponentProvider
import com.krucha.kotlinsample.di.app.AppComponent
import com.krucha.kotlinsample.di.app.DaggerAppComponent
import com.krucha.kotlinsample.utils.CustomDebugTree
import timber.log.Timber

class SampleApp : Application(), DaggerComponentProvider {

    override val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(context = applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(CustomDebugTree())
        }
    }

}