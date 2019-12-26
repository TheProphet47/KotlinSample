package com.krucha.kotlinsample.screen.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.di.injector
import com.krucha.kotlinsample.utils.PASSWORD_LENGTH
import com.krucha.kotlinsample.utils.afterTextChanged
import com.krucha.kotlinsample.screen.auth.login.model.LoggedInUser
import com.krucha.kotlinsample.screen.auth.login.model.LoginResult
import com.krucha.kotlinsample.screen.auth.login.model.LoginViewData
import com.krucha.kotlinsample.screen.auth.register.RegisterActivity
import com.krucha.kotlinsample.screen.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this, injector.loginViewModelFactory()).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindUi()
        bindViewModel()
    }

    private fun bindUi() {
        email.afterTextChanged { text -> viewModel.emailChanged(text) }
        password.afterTextChanged { text -> viewModel.passwordChanged(text) }
        password.setOnEditorActionListener { _, _, _ -> btnSignIn.callOnClick() }

        btnSignIn.setOnClickListener { viewModel.login(getLoginData()) }
        btnLinkToSignUp.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }

        LoginLog.debug("UI bound")
    }

    private fun bindViewModel() {
        viewModel.formState.observe(this, Observer {
            val viewState = it ?: return@Observer

            btnSignIn.isEnabled = viewState.isDataValid

            if (viewState.emailError != null) {
                email.error = getString(viewState.emailError)
            }

            if (viewState.passwordError != null) {
                password.error = getString(viewState.passwordError).format(PASSWORD_LENGTH)
            }
        })

        viewModel.loginResult.observe(this, Observer {
            when(val loginResult = it ?: return@Observer) {
                is LoginResult.Success -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    showLoginSucceeded(loginResult.user)
                }
                is LoginResult.Error -> showLoginFailed(loginResult.error)
            }
        })

        LoginLog.debug("ViewModel bound")
    }


    private fun getLoginData() = LoginViewData(email.text.toString(), password.text.toString())

    private fun showLoginSucceeded(loggedInUser: LoggedInUser) {
        val message = getString(loggedInUser.message)
        Toast.makeText(applicationContext, "$message ${loggedInUser.name}", Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
