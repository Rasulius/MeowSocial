package com.rossgramm.rossapp.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rossgramm.rossapp.base.Result
import com.rossgramm.rossapp.base.ui.ButtonState
import com.rossgramm.rossapp.base.ui.InputError
import com.rossgramm.rossapp.base.ui.SingleLiveEvent
import com.rossgramm.rossapp.login.domain.LoginInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EnterViewModel : ViewModel() {

    private val _userInputErrorLiveData = MutableLiveData<InputError?>(null)
    val userInputErrorLiveData: LiveData<InputError?>
        get() = _userInputErrorLiveData

    private val _passInputErrorLiveData = MutableLiveData<InputError?>(null)
    val passInputErrorLiveData: LiveData<InputError?>
        get() = _passInputErrorLiveData

    private val _buttonLiveData = MutableLiveData<ButtonState>(ButtonState.DISABLE)
    val buttonLiveData: LiveData<ButtonState>
        get() = _buttonLiveData

    val errorMessageLiveData = SingleLiveEvent<Boolean>()

    //TODO: rework no "true" navigation
    val toHomeScreenLiveData = SingleLiveEvent<Boolean>()

    private val interactor: LoginInteractor = LoginInteractor()

    fun login(login: String?, pass: String?) {
        inputDataChanged(login, pass)
        viewModelScope.launch(Dispatchers.IO) {
            _buttonLiveData.postValue(ButtonState.LOADING)

            //TODO: for debug purpose
            val result = if (pass == "111111") {
                Result.Success(Unit)
            } else {
                interactor.login(login ?: "", pass ?: "")
            }

            when (result) {
                is Result.Error -> errorMessageLiveData.postValue(true)
                is Result.Success -> {
                    //go to  the Home screen
                    toHomeScreenLiveData.postValue(true)
                }
            }
            launch(Dispatchers.Main) { inputDataChanged(login, pass) }
        }
    }


    fun inputDataChanged(login: String?, password: String?) {
        val isLoginValid = checkLogin(login)
        val isPassLogin = checkPass(password)
        val isValid = isLoginValid && isPassLogin
        _buttonLiveData.postValue(if (isValid) ButtonState.ENABLE else ButtonState.DISABLE)
    }

    private fun checkLogin(login: String?): Boolean {
        //для тестов убрал валидацию логина (в будущем будет возможность вводить не только email, но и никнейм
        //val isValid = Patterns.EMAIL_ADDRESS.matcher(login).matches()
        val isValid = true
        val isEmpty = login.isNullOrEmpty()
        _userInputErrorLiveData.value = if (!isValid && !isEmpty) InputError.Incorrect else null
        return isValid
    }

    private fun checkPass(pass: String?): Boolean {
        val isValid = pass?.length ?: 0 > 5
        val isEmpty = pass.isNullOrEmpty()
        _passInputErrorLiveData.value = if (!isValid && !isEmpty) InputError.Incorrect else null
        return isValid
    }

}