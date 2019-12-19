package com.krucha.kotlinsample.screen.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krucha.kotlinsample.CustomLog
import com.krucha.kotlinsample.features.auth.LoginRepository
import com.krucha.kotlinsample.features.auth.LoginSource
import com.krucha.kotlinsample.features.auth.RegisterRepository
import com.krucha.kotlinsample.features.auth.firebase.FirebaseAuth
import com.krucha.kotlinsample.screen.auth.login.LoginLog
import com.krucha.kotlinsample.screen.auth.login.LoginViewModel
import com.krucha.kotlinsample.screen.auth.register.RegisterLog
import com.krucha.kotlinsample.screen.auth.register.RegisterViewModel

class AuthViewModelFactory(val app: Application) : ViewModelProvider.AndroidViewModelFactory(app) {

    companion object {
        private val firebase = FirebaseAuth()
        private val registerRepository = RegisterRepository(firebase)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            LoginViewModel::class.java -> {
                LoginLog.debug("Create LoginViewModel")
                LoginViewModel(app, LoginRepository.getInstance()) as T
            }
            RegisterViewModel::class.java -> {
                RegisterLog.debug("Create RegisterViewModel")
                RegisterViewModel(app, registerRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}