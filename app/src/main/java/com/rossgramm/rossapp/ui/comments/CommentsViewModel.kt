package com.rossgramm.rossapp.ui.comments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rossgramm.rossapp.base.web.WebApi
import com.rossgramm.rossapp.comments.data.Comment
import com.rossgramm.rossapp.comments.data.FeedPostsRepository
import com.rossgramm.rossapp.comments.data.webAPI.GetCommentListAPI
import com.rossgramm.rossapp.managers.SessionManager
import com.rossgramm.rossapp.ui.common.BaseViewModel
import com.rossgramm.rossapp.user.data.User
import com.rossgramm.rossapp.user.data.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

// Модель комментария

class CommentsViewModel() :
    BaseViewModel() {
    private val getCommentsApiService = WebApi.getRetrofit()
        .create(GetCommentListAPI::class.java)
    private lateinit var feedPostsRepo: FeedPostsRepository
    private lateinit var usersRepo: UsersRepository
    private val commentList: MutableList<Comment> = mutableListOf()
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    val comments: LiveData<List<Comment>>
        get() = _comments

    private var _comments = MutableLiveData<List<Comment>>(emptyList())


    private lateinit var postId: String
    //val user: LiveData<User> = usersRepo.getUser()

    // Заправшиваются комментарии из репозитория. Который в свою очередь будет
    // дергать Rest функции, пока не реализовал

    fun init(postId: String) {
        this.postId = postId
        _comments = feedPostsRepo.getComments(postId)
    }

    private suspend fun getCommentsFromApi(postId: String): List<Comment> {
        if (SessionManager.getAccessToken() == null) {
            getFakeСomments() //заполнение списка комментариев тестовыми данными при отстутствии токена
        } else {
            getRealСomments(postId) //заполнение списка комментариев реальными данными с api
        }
        return commentList
    }

    private suspend fun getRealСomments(postId: String) {
        val response =
            getCommentsApiService.getCommentList(
                postId,
                "Bearer " + SessionManager.getAccessToken()
            )
        for (comment in response.comments) {
            val newRealComment = Comment()
            newRealComment.postCommentId = comment.id.toString()
            newRealComment.uid = comment.owner.id.toString()
            newRealComment.username = comment.owner.nickname
            newRealComment.photo = comment.owner.avatarLink
            newRealComment.text = comment.text
            newRealComment.timestamp = comment.createdAt
            commentList.add(newRealComment)
        }
    }

    private fun getFakeСomments(): List<Comment> {
        val commentsData = arrayOf(
            "великолепная статья!!!!",
            "статья огонь",
            "belissimo bravo!belissimo bravo!belissimo bravo!belissimo bravo!belissimo bravo!belissimo bravo!belissimo bravo!",
            "автору печенек",
            "Мне кажется, что мне не кажется, \n" +
                    "что ты тут точно отлично получилась."
        )
        for (i in 0..40) {
            val newFakeComment = Comment()
            newFakeComment.postCommentId = i.toString()
            newFakeComment.uid = i.toString()
            newFakeComment.username = "cristina@serbryakova"
            newFakeComment.photo = null
            newFakeComment.text = commentsData[(commentsData.indices).random()]
            newFakeComment.timestamp = currentDate
            commentList.add(newFakeComment)
        }
        return commentList
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    fun createComment(text: String, user: User) {
//        val time = DateTimeFormatter
//            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
//            .withZone(ZoneOffset.UTC)
//            .format(Instant.now())
//
//        val comment = Comment(
//            uid = user.uid,
//            username = user.username,
//            photo = user.photo,
//            text = text,
//            time
//        )
//
//        val onFailureListener = null
//        // Создаем комментарии
//        onFailureListener?.let {
//            feedPostsRepo.createComment(postId, comment).addOnFailureListener(
//                it
//            )
//        }
//    }

    fun loadComments(postId: String) {
        // загружаем фековые комментарии
        viewModelScope.launch(Dispatchers.IO) {
            val viewData = getCommentsFromApi(postId)
            _comments.postValue(viewData)
        }
    }
}