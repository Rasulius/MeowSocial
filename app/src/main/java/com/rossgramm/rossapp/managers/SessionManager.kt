package com.rossgramm.rossapp.managers

import com.rossgramm.rossapp.base.web.ApiTokenStore

//TODO: naming?  is it Manager??
object SessionManager {
    private const val SESSION_ACCOUNT_PREF = "account_id_session_pref"

    //TODO:DI
    private val preferencesManager = PreferencesManager()

    var currentAccountId: String? = null
        private set
        get() {
            if (field == null) {
                field = preferencesManager.getString(SESSION_ACCOUNT_PREF, null)
            }
            return field
        }

    var currentAccount: AccountDomainModel? = null
        private set


    fun saveSession(account: AccountDomainModel, apiToken: String) {
        currentAccount = account
        currentAccountId = account.id
        preferencesManager.putString(SESSION_ACCOUNT_PREF, account.id)
        ApiTokenStore.saveAccessToken(apiToken, account.id)
    }

    fun getAccessToken(): String? = currentAccountId?.let { ApiTokenStore.getAccessToken(it) }
}