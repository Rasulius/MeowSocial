package com.rossgramm.rossapp.login.domain

import com.rossgramm.rossapp.login.ui.LoggedInUserView

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)