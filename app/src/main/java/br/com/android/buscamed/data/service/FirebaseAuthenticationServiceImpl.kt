package br.com.android.buscamed.data.service

import br.com.android.buscamed.domain.exception.DomainAuthException
import br.com.android.buscamed.domain.model.UserCredentials
import br.com.android.buscamed.domain.service.AuthenticationService
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthenticationServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthenticationService {

    override suspend fun signIn(credentials: UserCredentials) {
        try {
            firebaseAuth.signInWithEmailAndPassword(credentials.email, credentials.password).await()
        } catch (e: Exception) {
            throw mapToDomainException(e)
        }
    }

    override suspend fun signUp(credentials: UserCredentials): String {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(credentials.email, credentials.password).await()
            return result.user?.uid!!
        } catch (e: Exception) {
            throw mapToDomainException(e)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    private fun mapToDomainException(exception: Exception): DomainAuthException {
        return when (exception) {
            is FirebaseAuthInvalidUserException -> DomainAuthException.InvalidCredentials(exception)
            is FirebaseAuthInvalidCredentialsException -> {
                if (exception.errorCode == "ERROR_INVALID_EMAIL") {
                    DomainAuthException.InvalidEmail(exception)
                } else {
                    DomainAuthException.InvalidCredentials(exception)
                }
            }
            is FirebaseNetworkException -> DomainAuthException.NetworkError(exception)
            is FirebaseAuthUserCollisionException -> DomainAuthException.EmailAlreadyInUse(exception)
            is DomainAuthException -> exception
            else -> throw exception
        }
    }
}