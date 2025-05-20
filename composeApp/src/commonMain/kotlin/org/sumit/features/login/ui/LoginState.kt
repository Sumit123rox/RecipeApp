package org.sumit.features.login.ui

sealed class LoginState {
    data object Idel : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Error(val message: String) : LoginState()

}