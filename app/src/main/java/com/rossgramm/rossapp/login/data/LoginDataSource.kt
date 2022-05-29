package com.rossgramm.rossapp.login.data

import com.rossgramm.rossapp.base.web.WebApi
import com.rossgramm.rossapp.login.data.webApi.LoginAPI
import com.rossgramm.rossapp.login.data.webApi.LoginRequest
import com.rossgramm.rossapp.login.data.webApi.LoginResponse


class LoginDataSource {
    //TODO: DI
    private val loginApiService = WebApi.getRetrofit()
        .create(LoginAPI::class.java)


    @Throws(Exception::class)
    suspend fun signin(username: String, password: String): LoginResponse {
    //for tests
    //return loginApiService.signin(LoginRequest(username, password))
        return loginApiService.signin(login = username, password = password)
    }

    fun logout() {
        // TODO: revoke authentication
    }
}