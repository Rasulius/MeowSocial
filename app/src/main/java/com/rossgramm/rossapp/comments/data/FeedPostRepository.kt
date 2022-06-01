package com.rossgramm.rossapp.comments.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rossgramm.rossapp.home.data.Post

/* Интерфейс для получения постов  комментариев и лайков, надо будет реализовать \
 свой класс который будет по номеру поста, реализовывать данный
  интерфейс, потом необходимо передать  провайдеру, данный класс*/

interface FeedPostsRepository {
    fun getFeedPost(uid: String, postId: String): LiveData<Post>
    fun getFeedPosts(uid: String): LiveData<List<Post>>
    fun getLikes(postId: String): LiveData<List<FeedPostLike>>
    fun getComments(postId: String): MutableLiveData<List<Comment>>
    abstract fun createComment(postId: String, comment: Comment): Comment
}

data class FeedPostLike(val userId: String)