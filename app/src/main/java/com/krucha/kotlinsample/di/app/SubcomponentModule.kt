package com.krucha.kotlinsample.di.app

import com.krucha.kotlinsample.di.activity.ActivityComponent
import dagger.Module

@Module(subcomponents = [ActivityComponent::class])
interface SubcomponentModule