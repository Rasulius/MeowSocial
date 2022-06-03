package com.rossgramm.rossapp.ui.home

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
    val generatedPostMutableList: MutableList<Post> = mutableListOf()
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())
    val generatedPost = Post()

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
            getPostsApiService.getPostsList(1, 10, "Bearer " + SessionManager.getAccessToken())
        for (post in response.posts) {
            generatedPost.author = post.owner.nickname
            generatedPost.canLike = true //временно - позже будет возможность отключить возможность лайкнуть
            generatedPost.created_time = post.createdAt
            generatedPost.isHidden = false //временно - позже будет возможность скрывать посты
            generatedPost.message = post.id.toString() + " - " + post.comment //id был добавлен для тестов
            generatedPost.address = "Санкт-Петербург, Россия" // временно - позже будет реальное местоположение
            generatedPost.picture_url =
                post.attachments[0].file.link //временно - позже будет реализована карусель
            generatedPost.updated_time = currentDate
            generatedPost.username = post.owner.nickname //в адаптере дублируется
            generatedPostMutableList.add(generatedPost)
        }
    }

    private fun createFakePost() {
        for (i in 0..5) {
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