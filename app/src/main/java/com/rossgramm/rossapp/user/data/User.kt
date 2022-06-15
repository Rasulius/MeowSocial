package com.rossgramm.rossapp.user.data

data class User(val uid: String,
                val name: String = "",
                val username: String = "",
                val email: String = "",
                val photo: String,
                val follows: Map<String, Boolean> = emptyMap(),
                val followers: Map<String, Boolean> = emptyMap(),
                val website: String? = null, val bio: String? = null, val phone: Long? = null) {


}