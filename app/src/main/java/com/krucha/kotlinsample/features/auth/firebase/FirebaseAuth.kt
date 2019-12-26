package com.krucha.kotlinsample.features.auth.firebase

import com.krucha.kotlinsample.di.scope.PerApp
import com.krucha.kotlinsample.features.auth.ILogin
import com.krucha.kotlinsample.features.auth.IRegister
import javax.inject.Inject

@PerApp
class FirebaseAuth @Inject constructor() : ILogin, IRegister {

    override fun login() {

    }

    override fun logout() {

    }

    override fun register() {

    }
}