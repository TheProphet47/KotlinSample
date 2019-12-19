package com.krucha.kotlinsample.utils

import android.util.Patterns
import androidx.annotation.StringRes
import com.krucha.kotlinsample.R

const val PASSWORD_LENGTH = 5

@StringRes
fun checkNameForError(name: String) : Int? = when {
    name.isBlank() -> R.string.invalid_name
    else -> null
}

@StringRes
fun checkEmailForError(email: String) : Int? = when {
    email.isBlank() -> R.string.invalid_email
    !email.contains('@') || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.invalid_email
    else -> null
}

@StringRes
fun checkPasswordForError(password: String) : Int? = when {
    password.isBlank() -> R.string.invalid_password
    password.length < PASSWORD_LENGTH -> R.string.invalid_password_length
    else -> null
}

@StringRes
fun checkRePasswordForError(passwords: TwoPasswords) : Int? = when {
    passwords.first != passwords.second -> R.string.invalid_re_password
    else -> null
}


data class TwoPasswords (
    val first: String,
    val second: String
)