package com.rossgramm.rossapp.ui.comments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rossgramm.rossapp.comments.data.Comment
import com.rossgramm.rossapp.comments.data.FeedPostsRepository
import com.rossgramm.rossapp.ui.common.BaseViewModel
import com.rossgramm.rossapp.user.data.User
import com.rossgramm.rossapp.user.data.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

// Модель комментария

class CommentsViewModel() :
    BaseViewModel() {

    private lateinit var feedPostsRepo: FeedPostsRepository
    private lateinit var usersRepo: UsersRepository

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

    private fun getFakeСomments(): List<Comment>{
        val commentsData = arrayOf("великолепная статья!!!!",
            "статья огонь",
            "belissimo bravo!belissimo bravo!belissimo bravo!belissimo bravo!belissimo bravo!belissimo bravo!belissimo bravo!",
            "автору печенек",
            "Мне кажется, что мне не кажется, \n" +
                    "что ты тут точно отлично получилась.")
        val randomComments: MutableList<Comment> = mutableListOf()
        for (i in 0..40) {
            val newRandomComment = Comment()
            newRandomComment.uid = i.toString()
            newRandomComment.text = commentsData[(commentsData.indices).random()]
            randomComments.add(newRandomComment)
        }
        return randomComments
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createComment(text: String, user: User) {
        val time = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())

        val comment = Comment(
            uid = user.uid,
            username = user.username,
            photo = user.photo,
            text = text,
            time)

        val onFailureListener = null
        // Создаем комментарии
        onFailureListener?.let {
            feedPostsRepo.createComment(postId, comment).addOnFailureListener(
                it
            )
        }


    }
    fun loadComments() {
        // загружаем фековые комментарии
        viewModelScope.launch(Dispatchers.IO) {
            val viewData = getFakeСomments()
            _comments.postValue(viewData)
        }
    }
}