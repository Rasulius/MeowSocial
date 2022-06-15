package com.rossgramm.rossapp.base.web

import com.rossgramm.rossapp.managers.PreferencesManager

object ApiTokenStore {

    private const val USER_TOKEN = "user_token"
    private val preferencesManager = PreferencesManager()


    private var accessToken: String? = null

    fun getAccessToken(accountId: String): String? {
        if (accessToken == null) {
            accessToken = prepareAccessToken(accountId)
        }
        return accessToken
    }

    fun isTokenExists(): Boolean {
        val savedAccessToken =
            preferencesManager.getString(USER_TOKEN, null)
        return !savedAccessToken.isNullOrEmpty()
    }

    fun saveAccessToken(token: String, accountId: String) {
        accessToken = token

        preferencesManager.putString(
            USER_TOKEN,
            encryptToken(token)
        )
    }


    fun removeToken() {
        accessToken = null
        preferencesManager.removeKey(USER_TOKEN)
    }


    private fun prepareAccessToken(accountId: String): String? {
        val storedAccessToken = preferencesManager.getString(USER_TOKEN, null)

        val token: String? = if (storedAccessToken != null && accountId != null) {
            decryptToken(storedAccessToken)
        } else null

        return token
    }

    private fun encryptToken(token: String): String {
        //TODO: implement encryption
        return token
    }

    private fun decryptToken(token: String): String {
        //TODO: implement encryption
        return token
    }
}