package com.rossgramm.rossapp.home.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rossgramm.rossapp.base.web.WebApi
import com.rossgramm.rossapp.home.data.Post
import com.rossgramm.rossapp.home.data.webAPI.GetPostListAPI
import com.rossgramm.rossapp.managers.SessionManager
import com.rossgramm.rossapp.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// Данный класс (модель, данных посты)

class HomeViewModel : BaseViewModel() {

    private val getPostsApiService = WebApi.getRetrofit()
        .create(GetPostListAPI::class.java)

    val posts: LiveData<List<Post>>
        get() = _posts

    private val _posts = MutableLiveData<List<Post>>(emptyList())
    val randomPosts: MutableList<Post> = mutableListOf()
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

    @SuppressLint("SimpleDateFormat")
    private suspend fun getPostsFromApi(): List<Post> {
        if (SessionManager.getAccessToken() == null) {
            createFakePost() //заполнение списка постов тестовыми данными при отстутствии токена
        } else {
            createRealPost() //заполнение списка постов реальными данными с api
        }
        return randomPosts
    }

    private suspend fun createRealPost() {
        val response =
            getPostsApiService.getPostsList(1, 10, "Bearer " + SessionManager.getAccessToken())
        for (post in response.posts) {
            val newRandomPost = Post()
            val currentDate = sdf.format(Date())
            newRandomPost.author = post.owner.nickname
            newRandomPost.canLike = true //временно - позже будет возможность отключить возможность лайкнуть
            newRandomPost.created_time = post.createdAt
            newRandomPost.isHidden = false //временно - позже будет возможность скрывать посты
            newRandomPost.message = post.id.toString() + " - " + post.comment //id был добавлен для тестов
            newRandomPost.address = "Санкт-Петербург, Россия" // временно - позже будет реальное местоположение
            newRandomPost.picture_url =
                post.attachments[0].file.link //временно - позже будет реализована карусель
            newRandomPost.updated_time = currentDate
            newRandomPost.username = post.owner.nickname //в адаптере дублируется
            randomPosts.add(newRandomPost)
        }
    }

    private fun createFakePost() {
        for (i in 0..5) {
            val newRandomPost = Post()
            val currentDate = sdf.format(Date())
            newRandomPost.author = "cristina@serbryakova"
            newRandomPost.canLike = true
            newRandomPost.created_time = currentDate
            newRandomPost.isHidden = false
            newRandomPost.message = "gorodnaneve Люблю тебя, Петра творенье,\n" +
                    "Люблю твой строгий, стройный вид... ещё"
            newRandomPost.address = "Санкт-Петербург, Россия"
            newRandomPost.picture_url = null
            newRandomPost.updated_time = currentDate
            newRandomPost.username = "Кристина"
            randomPosts.add(newRandomPost)
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





