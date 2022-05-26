package com.rossgramm.rossapp.ui.post

import com.rossgramm.rossapp.home.data.Post


interface PostItemUserActionsListener {
    fun onPostClicked(post: Post)
}