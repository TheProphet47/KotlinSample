package com.krucha.kotlinsample.data.repository

import com.krucha.kotlinsample.data.model.User
import com.krucha.kotlinsample.data.room.UserDao

class UserRepository(private val dao : UserDao) {
    val users = dao.getAllUsers()

    fun getUser(email: String) = dao.getUserBy(email)
    fun getUserWithFilms(email: String) = dao.getUserWithFilms(email)

    suspend fun insert(user: User) = dao.insert(user)
    suspend fun update(user: User) = dao.update(user)
    suspend fun delete(user: User) = dao.delete(user)
}