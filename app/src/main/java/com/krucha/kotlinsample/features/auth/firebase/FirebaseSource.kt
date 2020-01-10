package com.krucha.kotlinsample.features.auth.firebase

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.krucha.kotlinsample.di.scope.PerApp
import com.krucha.kotlinsample.features.auth.AuthUser
import com.krucha.kotlinsample.features.auth.ILogin
import com.krucha.kotlinsample.features.auth.IRegister
import com.krucha.kotlinsample.utils.Result
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@PerApp
class FirebaseSource @Inject constructor(private val auth: FirebaseAuth) : ILogin, IRegister {

    override val isAuth: Boolean
        get() = auth.currentUser != null

    override val user: AuthUser?
        get() = auth.currentUser?.let { mapToAuthUser(it) }

    override suspend fun register(email: String, password: String): Result<AuthUser> {
        Timber.d("Register with $email and $password")
        return authCall { mapToSuspend { auth.createUserWithEmailAndPassword(email, password) } }
    }

    override suspend fun login(email: String, password: String): Result<AuthUser> {
        Timber.d("Login with $email and $password")
        return authCall { mapToSuspend { auth.signInWithEmailAndPassword(email, password) } }
    }

    override fun logout() {
        Timber.d("Logout user ${auth.currentUser?.displayName}")
        auth.signOut()
    }


    private suspend fun authCall(firebaseCall: suspend () -> Task<AuthResult>): Result<AuthUser> {
        val authTask = firebaseCall()

        return if (authTask.isSuccessful && authTask.result?.user != null) {
            val authUser = mapToAuthUser(authTask.result?.user as FirebaseUser)
            Result.Success(authUser)
        } else {
            val exception = authTask.exception ?: Exception("Unknown exception")
            Result.Error(exception)
        }
    }

    private fun mapToAuthUser(user: FirebaseUser): AuthUser {
        return AuthUser(id = user.uid, email = user.email, name = user.displayName)
    }

    private suspend inline fun mapToSuspend(crossinline firebaseCall: () -> Task<AuthResult>) =
        suspendCoroutine<Task<AuthResult>> { continuation ->
            firebaseCall().addOnCompleteListener(ContinuationListener(continuation))
        }


    inner class ContinuationListener (private val continuation: Continuation<Task<AuthResult>>)
        : OnCompleteListener<AuthResult> {
        override fun onComplete(task: Task<AuthResult>) {
            continuation.resume(task)
        }
    }
}