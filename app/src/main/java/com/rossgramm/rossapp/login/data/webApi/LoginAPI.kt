package com.rossgramm.rossapp.login.data.webApi

import com.google.gson.annotations.SerializedName
import retrofit2.http.*

interface LoginAPI {
    //TODO: актуализировать

    companion object {
        private const val LOGIN_PATH = "/Auth/sign-in"
        private const val SIGNUP_PATH = "/Auth/sign-up"
        //private const val LOGIN_CONFIG_PATH = "/loginConfig"
    }

//временно изменил способ singIn - позже политика работы сервера будет пересмотрена

//    @POST(LOGIN_PATH)
//    suspend fun signin(@Body request: LoginRequest): LoginResponse

    @POST(LOGIN_PATH)
    suspend fun signin(
        @Query("login") login: String,
        @Query("password") password: String
    ): LoginResponse

    @POST(SIGNUP_PATH)
    suspend fun signup(@Body request: RegisterRequest): RegisterResponse

/*    @GET(LOGIN_CONFIG_PATH)
    suspend fun getLoginConfig(): LoginConfigResponse*/
}

class LoginRequest(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val pass: String
)

class LoginResponse(
    @SerializedName("jwtToken")
    val token: String?,
    val account: AccountDto?
) {
    class AccountDto(
        val id: Long,
        val nickname: String?,
        val isVerified: Boolean = false,
        val avatarLink: String?,
        val name: String?,
        val bio: String?,
        val followerCount: Int,
        val followingCount: Int,
        val postsCount: Int
    )
}

class RegisterRequest
class RegisterResponse
//class LoginConfigResponse
