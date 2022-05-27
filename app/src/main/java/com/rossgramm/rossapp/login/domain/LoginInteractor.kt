package com.rossgramm.rossapp.login.domain

import com.rossgramm.rossapp.base.DomainException
import com.rossgramm.rossapp.base.Result
import com.rossgramm.rossapp.login.data.LoginDataSource
import com.rossgramm.rossapp.managers.SessionManager
import com.rossgramm.rossapp.managers.toDomainModel

class LoginInteractor {
    //TODO:  DI
    val repository = LoginRepository(LoginDataSource())

    suspend fun login(login: String, pass: String): Result<Unit> {
        val result = repository.signin(login, pass)
        return when (result) {
            is Result.Success -> {
                val token = result.data.token
                val account = result.data.account?.toDomainModel()
                if (token == null || account == null) return Result.Error(DomainException())
                SessionManager.saveSession(account, token)
                Result.Success(Unit)
            }
            is Result.Error -> Result.Error(result.exception)
        }
    }
}


