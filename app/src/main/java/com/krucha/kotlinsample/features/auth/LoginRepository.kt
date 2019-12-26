package com.krucha.kotlinsample.features.auth

import com.krucha.kotlinsample.data.model.User
import com.krucha.kotlinsample.di.scope.PerApp
import com.krucha.kotlinsample.features.auth.firebase.FirebaseAuth
import javax.inject.Inject

@PerApp
class LoginRepository @Inject constructor(private val source: LoginSource) {

    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null


    fun login(username: String, password: String, callback: (Boolean) -> Unit) {
        source.login(username, password) {
            user = it
            callback(user != null)
        }
    }

    fun logout() {
        source.logout()
        user = null
    }
}