package com.krucha.kotlinsample.features.auth

import com.krucha.kotlinsample.data.TestData
import com.krucha.kotlinsample.data.model.User
import javax.inject.Inject
import kotlin.random.Random

class LoginSource @Inject constructor(private val service: ILogin) {

    fun login(username: String, password: String, callback: (User?) -> Unit) {
        service.login()
        callback(if (Random.nextInt(2) == 0) TestData.user else null)
    }

    fun logout() {
        service.logout()
    }
}