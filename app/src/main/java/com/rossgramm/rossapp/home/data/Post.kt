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
    var isHidden: Boolean = false,
    var likes: List<String>? = null,
    var canLike: Boolean? = false,
    var username: String? = null,
    var author: String? = null,
    var address: String? = null,
    var picture_url: String? = null,
    var user : RossgrammUser? = RossgrammUser("1", "default_user")
){
    class RossgrammUser( val id:String, val name :String )
}