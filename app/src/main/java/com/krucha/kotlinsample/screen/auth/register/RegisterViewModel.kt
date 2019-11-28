package com.krucha.kotlinsample.screen.auth.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.features.auth.RegisterRepository
import com.krucha.kotlinsample.screen.auth.*
import com.krucha.kotlinsample.screen.auth.register.model.*

class RegisterViewModel(app: Application, private val registerRepository: RegisterRepository) : AndroidViewModel(app) {

    private val mFormState = MutableLiveData<RegisterFormState>()
    private val mRegisterResult = MutableLiveData<RegisterResult>()

    private val viewData = MutableLiveData<RegisterViewData>(RegisterViewData())

    val formState: LiveData<RegisterFormState> = mFormState
    val registerResult: LiveData<RegisterResult> = mRegisterResult


    fun nameChanged(name: String) {
        check { state -> state.copy(nameError = checkNameForError(name)) }
        viewData.value?.name = name
    }

    fun emailChanged(email: String) {
        check { state -> state.copy(emailError = checkEmailForError(email)) }
        viewData.value?.email = email
    }

    fun passwordChanged(password: String) {
        check { state -> state.copy(passwordError = checkPasswordForError(password)) }
        viewData.value?.password = password

        val rePassword = viewData.value?.rePassword
        if (!rePassword.isNullOrEmpty()) {
            check { state -> state.copy(rePasswordError = checkRePasswordForError(TwoPasswords(password, rePassword))) }
        }
    }

    fun rePasswordChanged(rePassword: String) {
        viewData.value?.rePassword = rePassword

        val password = viewData.value?.password
        if (!password.isNullOrEmpty()) {
            check { state -> state.copy(rePasswordError = checkRePasswordForError(TwoPasswords(password, rePassword))) }
        }
    }

    fun register(loginData: RegisterViewData) {
        val email = loginData.email
        val password = loginData.password

        if (!email.isNullOrBlank() && !password.isNullOrBlank()) {
            registerRepository.register(email, password) { success ->
                if (success != null) {
                    val registeredUser = RegisteredUser(R.string.register_succeed)
                    mRegisterResult.value = RegisterResult.Success(registeredUser)
                } else {
                    mRegisterResult.value = RegisterResult.Error(R.string.login_failed)
                }
            }
        }
    }

    private fun check(validator: (RegisterFormState) -> RegisterFormState) {
        val newState = validator(mFormState.value ?: RegisterFormState())

        val isValid = newState.nameError == null
                && newState.emailError == null
                && newState.passwordError == null
                && newState.rePasswordError == null
                && viewData.value?.isFilled() ?: false

        mFormState.value = newState.copy(isDataValid = isValid)
        RegisterLog.debug("state: ${mFormState.value}")
    }
}