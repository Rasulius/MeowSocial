package com.rossgramm.rossapp.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rossgramm.rossapp.base.web.WebApi
import com.rossgramm.rossapp.comments.data.Comment
import com.rossgramm.rossapp.home.data.Post
import com.rossgramm.rossapp.home.data.webAPI.GetPostListAPI
import com.rossgramm.rossapp.managers.SessionManager
import com.rossgramm.rossapp.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

// Данный класс (модель, данных посты)

class HomeViewModel : BaseViewModel() {

    private val getPostsApiService = WebApi.getRetrofit()
        .create(GetPostListAPI::class.java)

    val posts: LiveData<List<Post>>
        get() = _posts

    private val _posts = MutableLiveData<List<Post>>(emptyList())
    val generatedPostMutableList: MutableList<Post> = mutableListOf()
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    @SuppressLint("SimpleDateFormat")
    private suspend fun getPostsFromApi(): List<Post> {
        if (SessionManager.getAccessToken() == null) {
            createFakePost() //заполнение списка постов тестовыми данными при отстутствии токена
        } else {
            createRealPost() //заполнение списка постов реальными данными с api
        }
        return generatedPostMutableList
    }

    private suspend fun createRealPost() {
        try {
            val response = getPostsApiService.getPostsList()
            response.posts.forEach { post ->
                val generatedPost = Post().apply {
                    id = post.id.toString()
                    created_time = post.createdAt
                    updated_time = currentDate
                    message = post.comment
                    isHidden = false
                    likes = emptyList()
                    canLike = true
                    username = post.owner.nickname
                    author = post.owner.nickname
                    authorAvatar = post.owner.avatarLink
                    address = "Санкт-Петербург, Россия"
                    picture_url = post.attachments[0].file.link
                    user?.id = post.owner.id.toString()
                    user?.name = post.owner.nickname
                    user?.avatarLink = post.owner.avatarLink
                }
                generatedPostMutableList.add(generatedPost)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("MyApp", "Server get posts error")
            getErrorPost()
        }
    }

    private fun getErrorPost() {
        val errorPost = Post()
        errorPost.id = "unknown"
        errorPost.author = "unknown"
        errorPost.canLike = true
        errorPost.created_time = currentDate
        errorPost.isHidden = false
        errorPost.message = "Connection error. Please try later."
        errorPost.address = "unknown"
        errorPost.picture_url = null
        errorPost.updated_time = currentDate
        errorPost.username = "unknown"
        generatedPostMutableList.add(errorPost)
    }

    private fun createFakePost() {
        for (i in 0..5) {
            val generatedPost = Post()
            generatedPost.id = "1212${i}"
            generatedPost.author = "cristina@serbryakova"
            generatedPost.canLike = true
            generatedPost.created_time = currentDate
            generatedPost.isHidden = false
            generatedPost.message = "gorodnaneve Люблю тебя, Петра творенье,\n" +
                    "Люблю твой строгий, стройный вид... ещё"
            generatedPost.address = "Санкт-Петербург, Россия"
            generatedPost.picture_url = null
            generatedPost.updated_time = currentDate
            generatedPost.username = "Кристина"
            generatedPostMutableList.add(generatedPost)
        }
    }

    fun loadPosts() {
        // This is a coroutine scope with the lifecycle of the ViewModel
        viewModelScope.launch(Dispatchers.IO) {
            getPostsFromApi()
            val viewData = getPostsFromApi()
            _posts.postValue(viewData)
        }
    }
}