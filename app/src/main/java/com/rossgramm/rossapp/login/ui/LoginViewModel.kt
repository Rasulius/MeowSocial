package com.rossgramm.rossapp.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.rossgramm.rossapp.login.domain.LoginRepository
import com.rossgramm.rossapp.base.Result

import com.rossgramm.rossapp.R
import com.rossgramm.rossapp.login.domain.LoginResult
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    init {

    }

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            val result = loginRepository.login(username, password)
            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = ""))
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
    }


    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}