package com.rossgramm.rossapp.base.ui

sealed class InputError {
    object Incorrect : InputError()
    class Special(val errorText: String) : InputError()
}

//TODO: move and add  string resource
fun InputError?.text() = when (this) {
    is InputError.Incorrect -> "Incorrect input"
    is InputError.Special -> this.errorText
    null -> null
}

enum class ButtonState { ENABLE, DISABLE, LOADING }