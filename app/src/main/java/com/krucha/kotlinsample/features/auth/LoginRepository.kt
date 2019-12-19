package com.krucha.kotlinsample.features.auth

import com.krucha.kotlinsample.data.model.User
import com.krucha.kotlinsample.features.auth.firebase.FirebaseAuth

class LoginRepository(private val source: LoginSource) {

    companion object {
        @Volatile
        private var INSTANCE: LoginRepository? = null

        fun getInstance() : LoginRepository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = LoginRepository(LoginSource(FirebaseAuth()))
                INSTANCE = instance
                return instance
            }
        }
    }

    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null


    fun login(username: String, password: String, callback: (Boolean) -> Unit) {
        val lock = Object()
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