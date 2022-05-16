package com.rossgramm.rossapp.login.data

import com.rossgramm.rossapp.base.Result
import com.rossgramm.rossapp.base.web.WebApi
import com.rossgramm.rossapp.login.data.webApi.LoginAPI
import com.rossgramm.rossapp.login.data.webApi.LoginRequest
import com.rossgramm.rossapp.login.data.webApi.LoginResponse
import java.io.IOException


class LoginDataSource {
    //TODO: DI
    private val loginApiService = WebApi.getRetrofit()
        .create(LoginAPI::class.java)

    @Throws(Exception::class)
    suspend fun  login(username: String, password: String): Result<LoginResponse> {
        try {
            // TODO: handle loggedInUser authentication
            val result =     loginApiService.login(LoginRequest())
            return Result.Success(result)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}