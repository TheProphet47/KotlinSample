package com.krucha.kotlinsample.utils

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val ex: Exception) : Result<Nothing>()

    val succeeded: Boolean
        get() = this is Success && data != null

    override fun toString(): String {
        return when (this) {
            is Success -> "Success: $data"
            is Error -> "Error: $ex"
        }
    }
}