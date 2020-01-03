package com.krucha.kotlinsample.data

import com.krucha.kotlinsample.data.model.Film
import com.krucha.kotlinsample.data.model.User
import java.util.*

object TestData {

    val user = User("email", "MyUserName", 20, User.Gender.MAN, Date(), id = "3")

    val film1 = Film("Film 1", 2000, "come desc 1", userId = "3")
    val film2 = Film("Film 2", 2010, "come desc 2", userId = "3")
}