package com.rossgramm.rossapp.home.data


/**
 * Данный класс описывает единичный пост. *
 * @author Rasul
 * @property author имя которое мы видим у пользователя в ленте
 *
 */

data class Post(
    var id: String? = null,
    var created_time: String? = null,
    var updated_time: String? = null,
    var message: String? = null,
    var isHidden: Boolean? = null,
    var likes: List<String>? = null,
    var canLike: Boolean? = null,
    var username: String? = null,
    var author: String? = null,
    var authorAvatar: String? = null,
    var address: String? = null,
    var picture_url: String? = null,
    var user: RossgrammUser? = null
)

data class RossgrammUser(
    var id: String? = null,
    var name: String? = null,
    var avatarLink: String? = null
)