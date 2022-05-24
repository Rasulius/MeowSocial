package com.rossgramm.rossapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rossgramm.rossapp.home.data.Post
import com.rossgramm.rossapp.ui.common.BaseViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// Данный класс (модель, данных посты)

class HomeViewModel: BaseViewModel() {

    val posts: LiveData<List<Post>>
        get() = _posts

    private val _posts = MutableLiveData<List<Post>>(emptyList())

    private fun getFakePosts(): List<Post>{

        val randomPosts: MutableList<Post> = mutableListOf()

        for (i in 0..5) {
            val newRandomPost = Post()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            newRandomPost.author = "cristina@serbryakova"
            newRandomPost.canLike = true
            newRandomPost.created_time = currentDate
            newRandomPost.isHidden = false
            newRandomPost.message = "gorodnaneve Люблю тебя, Петра творенье,\n" +
                                    "Люблю твой строгий, стройный вид... ещё"
            newRandomPost.address = "Санкт-Петербург, Россия"
            newRandomPost.picture_url = "https://lookw.ru/12/1206/1567237510-89.jpg"
            newRandomPost.updated_time = currentDate
            newRandomPost.username = "Кристина"
            randomPosts.add(newRandomPost)
        }
        return randomPosts
    }

    // Функция заполняет фейковыми данными, пока до запроса, рест служит исключительно
    // для тестирования.

    fun loadPosts() {
        // This is a coroutine scope with the lifecycle of the ViewModel
        viewModelScope.launch {
            getFakePosts()
            val viewData = getFakePosts()
            _posts.postValue(viewData)
        }
    }

}





