package com.rossgramm.rossapp.login.domain

import com.rossgramm.rossapp.login.data.LoginDataSource
import com.rossgramm.rossapp.base.Result
import com.rossgramm.rossapp.login.data.model.LoggedInUser
import com.rossgramm.rossapp.login.data.webApi.LoginResponse

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    init {
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<LoginResponse> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(LoggedInUser("",""/*from result*/))
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}