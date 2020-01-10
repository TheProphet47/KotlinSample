package com.krucha.kotlinsample.screen.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.features.auth.LoginRepository
import com.krucha.kotlinsample.utils.checkEmailForError
import com.krucha.kotlinsample.utils.checkPasswordForError
import com.krucha.kotlinsample.screen.auth.login.model.LoggedInUser
import com.krucha.kotlinsample.screen.auth.login.model.LoginViewData
import com.krucha.kotlinsample.screen.auth.login.model.LoginResult
import com.krucha.kotlinsample.screen.auth.login.model.LoginFormState
import com.krucha.kotlinsample.utils.Result
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    private val mFormState = MutableLiveData<LoginFormState>()
    private val mLoginResult = MutableLiveData<LoginResult>()

    private val viewData = MutableLiveData<LoginViewData>(LoginViewData())

    val formState: LiveData<LoginFormState> = mFormState
    val loginResult: LiveData<LoginResult> = mLoginResult

    init {
        if (loginRepository.isAuth) {
            val loggedInUser = LoggedInUser(loginRepository.user?.name, R.string.login_succeed)
            mLoginResult.value = LoginResult.Success(loggedInUser)
        }
    }

    fun emailChanged(email: String) {
        check { state -> state.copy(emailError = checkEmailForError(email)) }
        viewData.value?.email = email
    }

    fun passwordChanged(password: String) {
        check { state -> state.copy(passwordError = checkPasswordForError(password)) }
        viewData.value?.password = password
    }

    fun login(loginData: LoginViewData) {
        if (!loginData.isFilled() || mFormState.value?.isDataValid != true) return

        viewModelScope.launch {
            val loginResult = loginRepository.login(loginData.email as String, loginData.password as String)

            Timber.d("Login result: $loginResult")
            if (loginResult is Result.Success) {
                val loggedInUser = LoggedInUser(loginResult.data.name, R.string.login_succeed)
                mLoginResult.value = LoginResult.Success(loggedInUser)
            } else {
                mLoginResult.value = LoginResult.Error(R.string.login_failed)
            }
        }
    }

    private fun check(validator: (LoginFormState) -> LoginFormState) {
        val newState = validator(mFormState.value ?: LoginFormState())

        val isValid = newState.emailError == null
                && newState.passwordError == null
                && viewData.value?.isFilled() ?: false

        mFormState.value = newState.copy(isDataValid = isValid)
    }
}