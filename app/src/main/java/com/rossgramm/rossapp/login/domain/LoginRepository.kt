package com.rossgramm.rossapp.login.domain

import com.rossgramm.rossapp.base.Result
import com.rossgramm.rossapp.login.data.LoginDataSource
import com.rossgramm.rossapp.login.data.webApi.LoginResponse
import java.io.IOException

class LoginRepository(val dataSource: LoginDataSource) {

    suspend fun signin(username: String, password: String): Result<LoginResponse> {
        try {
            val result = dataSource.signin(username, password)
            return Result.Success(result)
        } catch (e: Throwable) {
            //TODO: handle different types of exceptions
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        //user = null
        dataSource.logout()
    }

}