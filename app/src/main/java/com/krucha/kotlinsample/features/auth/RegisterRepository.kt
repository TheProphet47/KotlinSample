package com.krucha.kotlinsample.features.auth

import com.krucha.kotlinsample.data.TestData
import com.krucha.kotlinsample.data.model.User
import javax.inject.Inject
import kotlin.random.Random

class RegisterRepository @Inject constructor(private val service: IRegister) {

    fun register(username: String, password: String, callback: (User?) -> Unit) {
        service.register()

        callback(if (Random.nextInt(2) == 0) TestData.user else null)
    }
}