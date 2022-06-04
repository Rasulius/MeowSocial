package com.rossgramm.rossapp.ui.home

import android.annotation.SuppressLint
import android.util.Log
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
        val response =
            getPostsApiService.getPostsList("Bearer " + SessionManager.getAccessToken())
        for (post in response.posts) {
            val generatedPost = Post()
            generatedPost.id = post.id.toString()
            generatedPost.created_time = post.createdAt
            generatedPost.updated_time = currentDate
            generatedPost.message = post.comment
            generatedPost.isHidden = false
            generatedPost.likes = emptyList()
            generatedPost.canLike = true
            generatedPost.username = post.owner.nickname
            generatedPost.author = post.owner.nickname
            generatedPost.authorAvatar = post.owner.avatarLink
            generatedPost.address = "Санкт-Петербург, Россия"
            generatedPost.picture_url = post.attachments[0].file.link
            generatedPost.user?.id = post.owner.id.toString()
            generatedPost.user?.name = post.owner.nickname
            generatedPost.user?.avatarLink = post.owner.avatarLink
            generatedPostMutableList.add(generatedPost)
            Log.d("MyApp", generatedPost.toString())
        }
    }

    private fun createFakePost() {
        for (i in 0..5) {
            val generatedPost = Post()
            generatedPost.id="1212${i}"
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