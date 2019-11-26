package com.krucha.kotlinsample.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.krucha.kotlinsample.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM ${User.Table.NAME}")
    fun getAllUsers() : LiveData<List<User>>

    @Query("SELECT * FROM ${User.Table.NAME} WHERE ${User.Field.EMAIL} == :email")
    fun getUserBy(email: String) : LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}