package com.rossgramm.rossapp.managers

import com.rossgramm.rossapp.login.data.webApi.LoginResponse

data class AccountDomainModel(
    val id: String,
    val nickname: String?,
    val isVerified: Boolean = false,
    val avatarLink: String?,
    val name: String?,
    val bio: String?,
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val postsCount: Int = 0
)

fun LoginResponse.AccountDto.toDomainModel(): AccountDomainModel {
    return AccountDomainModel(
        id = id.toString(),
        nickname = nickname,
        isVerified = isVerified,
        avatarLink = avatarLink,
        name = name,
        bio = bio,
        followerCount = followerCount,
        followingCount = followingCount,
        postsCount = postsCount
    )
}