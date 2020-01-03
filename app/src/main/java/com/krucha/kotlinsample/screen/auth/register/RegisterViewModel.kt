package com.krucha.kotlinsample.screen.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.data.model.User
import com.krucha.kotlinsample.data.repository.UserRepository
import com.krucha.kotlinsample.features.auth.RegisterRepository
import com.krucha.kotlinsample.screen.auth.register.model.*
import com.krucha.kotlinsample.utils.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val userRepository: UserRepository
) : ViewModel() {

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
            check { state -> state.copy(
                rePasswordError = checkRePasswordForError(TwoPasswords(password, rePassword)))
            }
        }
    }

    fun rePasswordChanged(rePassword: String) {
        viewData.value?.rePassword = rePassword

        val password = viewData.value?.password
        if (!password.isNullOrEmpty()) {
            check { state -> state.copy(
                rePasswordError = checkRePasswordForError(TwoPasswords(password, rePassword)))
            }
        }
    }

    fun register(registerData: RegisterViewData) {
        if (!registerData.isFilled()) return

        viewModelScope.launch {
            val registerResult = registerRepository.register(registerData.email as String, registerData.password as String)

            Timber.d("Register result: $registerResult")
            if (registerResult is Result.Success) {
                val authUser = registerResult.data
                val dbUser = User(id = authUser.id, email = authUser.email, name = authUser.name, gender = registerData.gender)
                userRepository.insert(dbUser)

                val registeredUser = RegisteredUser(R.string.register_succeed)
                mRegisterResult.value = RegisterResult.Success(registeredUser)
            } else {
                mRegisterResult.value = RegisterResult.Error(R.string.login_failed)
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
    }
}