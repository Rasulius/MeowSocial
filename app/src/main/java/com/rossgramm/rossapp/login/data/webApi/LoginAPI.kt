package com.rossgramm.rossapp.login.data.webApi

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginAPI {
    //TODO: актуализировать

    companion object{
        private const val LOGIN_PATH = "/login"
        private const val REGISTER_PATH = "/register"
        private const val LOGIN_CONFIG_PATH = "/loginConfig"

    }

    @POST(LOGIN_PATH)
    suspend fun login(@Body request: LoginRequest ): LoginResponse

    @POST(REGISTER_PATH)
    suspend fun register(@Body request: RegisterRequest ): RegisterResponse

    @GET(LOGIN_CONFIG_PATH)
    suspend fun getLoginConfig(): LoginConfigResponse
}

class LoginRequest
class LoginResponse
class RegisterRequest
class RegisterResponse
class LoginConfigResponse
