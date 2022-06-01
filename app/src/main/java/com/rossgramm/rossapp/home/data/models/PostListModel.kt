package com.rossgramm.rossapp.home.data.models

data class PostListModel(
    var posts: List<PostContent>
)

data class PostContent(
    var owner: Owner,
    var createdAt: String,
    var attachments: List<Attachment>,
    var comment: String,
    var likesCount: Int,
    var isLiked: Boolean,
    var commentsCount: Int,
    var isFavorite: Boolean,
    var id: Int
)

data class Owner(
    var nickname: String,
    var isVerified: Boolean,
    var id: Int
)

data class Attachment(
    var file: PostContentFile,
    var order: Int,
    var attachmentType: String,
    var id: Int
)

data class PostContentFile(
    var type: String,
    var fullName: String,
    var link: String,
    var id: Int
)