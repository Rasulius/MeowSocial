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

    @POST(LOGIN_PATH)
    suspend fun signin(@Body request: LoginRequest): LoginResponse

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
        val followerCount: Int = 0,
        val followingCount: Int = 0,
        val postsCount: Int = 0
    )
}

class RegisterRequest
class RegisterResponse
//class LoginConfigResponse
