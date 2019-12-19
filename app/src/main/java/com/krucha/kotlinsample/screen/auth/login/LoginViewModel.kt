package com.krucha.kotlinsample.screen.auth.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.data.model.User
import com.krucha.kotlinsample.features.auth.LoginRepository
import com.krucha.kotlinsample.utils.checkEmailForError
import com.krucha.kotlinsample.utils.checkPasswordForError
import com.krucha.kotlinsample.screen.auth.login.model.LoggedInUser
import com.krucha.kotlinsample.screen.auth.login.model.LoginViewData
import com.krucha.kotlinsample.screen.auth.login.model.LoginResult
import com.krucha.kotlinsample.screen.auth.login.model.LoginFormState

class LoginViewModel(app: Application, private val loginRepository: LoginRepository) : AndroidViewModel(app) {

    private val mFormState = MutableLiveData<LoginFormState>()
    private val mLoginResult = MutableLiveData<LoginResult>()

    private val viewData = MutableLiveData<LoginViewData>(LoginViewData())

    val formState: LiveData<LoginFormState> = mFormState
    val loginResult: LiveData<LoginResult> = mLoginResult


    fun emailChanged(email: String) {
        check { state -> state.copy(emailError = checkEmailForError(
            email
        )
        ) }
        viewData.value?.email = email
    }

    fun passwordChanged(password: String) {
        check { state -> state.copy(passwordError = checkPasswordForError(
            password
        )
        ) }
        viewData.value?.password = password
    }

    fun login(loginData: LoginViewData) {
        val email = loginData.email
        val password = loginData.password

        if (!email.isNullOrBlank() && !password.isNullOrBlank()) {
            loginRepository.login(email, password) { success ->
                if (success) {
                    val user = loginRepository.user as User
                    val loggedInUser =
                        LoggedInUser(user.name, R.string.login_succeed)
                    mLoginResult.value = LoginResult.Success(loggedInUser)
                } else {
                    mLoginResult.value = LoginResult.Error(R.string.login_failed)
                }
            }
        }
    }

    private fun check(validator: (LoginFormState) -> LoginFormState) {
        val newState = validator(mFormState.value ?: LoginFormState())

        val isValid = newState.emailError == null
                && newState.passwordError == null
                && viewData.value?.isFilled() ?: false

        mFormState.value = newState.copy(isDataValid = isValid)
        LoginLog.debug("state: ${mFormState.value}")
    }
}