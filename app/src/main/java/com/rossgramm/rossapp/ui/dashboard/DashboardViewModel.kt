package com.rossgramm.rossapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rossgramm.rossapp.dashboard.data.AlbumItem
import com.rossgramm.rossapp.home.data.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardViewModel : ViewModel() {

    val album: LiveData<ArrayList<AlbumItem>>
        get() = _album
    private val _album = MutableLiveData<ArrayList<AlbumItem>>(ArrayList())

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private fun getFakePosts(): ArrayList<AlbumItem>{

        val randomPosts: ArrayList<AlbumItem> =  ArrayList<AlbumItem>()

        for (i in 0..50) {
            val newRandomPost = AlbumItem("","")
            randomPosts.add(newRandomPost)
        }
        return randomPosts
    }

    // Функция заполняет фейковыми данными, пока до запроса, рест служит исключительно
    // для тестирования.

    fun loadAlbums() {
        // This is a coroutine scope with the lifecycle of the ViewModel
        viewModelScope.launch(Dispatchers.IO) {
            getFakePosts()
            val viewData = getFakePosts()
            _album.postValue(viewData)
        }
    }


}