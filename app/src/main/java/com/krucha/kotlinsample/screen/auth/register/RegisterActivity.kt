package com.krucha.kotlinsample.screen.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.krucha.kotlinsample.R
import com.krucha.kotlinsample.data.model.User
import com.krucha.kotlinsample.di.injector
import com.krucha.kotlinsample.utils.PASSWORD_LENGTH
import com.krucha.kotlinsample.utils.afterTextChanged
import com.krucha.kotlinsample.screen.auth.login.LoginActivity
import com.krucha.kotlinsample.screen.auth.register.model.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.email
import kotlinx.android.synthetic.main.activity_register.password

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProvider(this, injector.registerViewModelFactory()).get(RegisterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        bindUi()
        bindViewModel()
    }

    private fun bindUi() {
        name.afterTextChanged { text -> viewModel.nameChanged(text) }
        email.afterTextChanged { text -> viewModel.emailChanged(text) }
        password.afterTextChanged { text -> viewModel.passwordChanged(text) }
        rePassword.afterTextChanged { text -> viewModel.rePasswordChanged(text) }

        btnSignUp.setOnClickListener { viewModel.register(getRegisterViewData()) }
        btnLinkToSignIn.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }
    }

    private fun bindViewModel() {
        viewModel.formState.observe(this, Observer {
            val viewState = it ?: return@Observer

            btnSignUp.isEnabled = viewState.isDataValid

            if (viewState.nameError != null) {
                name.error = getString(viewState.nameError)
            }
            if (viewState.emailError != null) {
                email.error = getString(viewState.emailError)
            }
            if (viewState.passwordError != null) {
                password.error = getString(viewState.passwordError)
                    .format(PASSWORD_LENGTH)
            }

            rePassword.error = if (viewState.rePasswordError != null)
                getString(viewState.rePasswordError) else null
        })

        viewModel.registerResult.observe(this, Observer {
            when (val registerResult = it ?: return@Observer) {
                is RegisterResult.Success -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    showRegisterSucceeded(registerResult.user)
                }
                is RegisterResult.Error -> showRegisterFailed(registerResult.error)
            }
        })
    }


    private fun getRegisterViewData() = RegisterViewData(
        name.text.toString(),
        email.text.toString(),
        password.text.toString(),
        rePassword.text.toString(),
        getCheckedGender())

    private fun getCheckedGender() = when (rgGender.checkedRadioButtonId) {
        rbMan.id -> User.Gender.MAN
        rbWoman.id -> User.Gender.WOMAN
        else -> User.Gender.MAN
    }

    private fun showRegisterSucceeded(registeredUser: RegisteredUser) {
        val message = getString(registeredUser.message)
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
