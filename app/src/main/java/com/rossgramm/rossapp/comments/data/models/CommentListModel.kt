package com.rossgramm.rossapp.comments.data.models

import com.rossgramm.rossapp.home.data.models.Owner

data class CommentListModel(
    var comments: List<PostComment>
)

data class PostComment(
    var owner: Owner,
    var createdAt: String,
    var text: String,
    var likesCount: Int,
    var isLiked: Boolean,
    var id: Int
)