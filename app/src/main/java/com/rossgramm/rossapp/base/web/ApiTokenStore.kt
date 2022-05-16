package com.rossgramm.rossapp.base.web

object ApiTokenStore {

    var accessToken: String? = null
        private set
        get() {
            if (field == null) {
                field = prepareAccessToken()
            }
            return field
        }

    fun isTokenExists(): Boolean {
        //TODO: get from local storage
        val savedAccessToken = ""
        return !savedAccessToken.isNullOrEmpty()
    }

    fun saveAcessToken(token: String, accountSpn: String?) {
        accessToken = token
        //TODO: encrypt and put  to local storage

       /* preferencesManager.putString(
            KEY_ACCESS_TOKEN,
            encryptData(token, "")
        )*/
    }


    fun removeToken() {
        accessToken = null
        //TODO: remove from local storage
        //preferencesManager.removeKey(KEY_ACCESS_TOKEN)
    }

    private fun prepareAccessToken(): String? {
        //TODO: get from local storage
        val storedAccessToken = ""//preferencesManager.getString(KEY_ACCESS_TOKEN, null)

        val token: String? = if (storedAccessToken != null) {
            ""
            //TODO: dencrypt
           /*decryptData(
                storedAccessToken,
                ""
            )*/
        } else null

        return token
    }
}