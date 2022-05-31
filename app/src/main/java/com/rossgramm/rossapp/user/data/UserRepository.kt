package com.rossgramm.rossapp.user.data

import androidx.lifecycle.LiveData

interface UsersRepository {
    fun getUsers(): LiveData<List<User>>
    fun currentUid(): String?
    fun getUser(): LiveData<User>
    fun getUser(uid: String): LiveData<User>
}