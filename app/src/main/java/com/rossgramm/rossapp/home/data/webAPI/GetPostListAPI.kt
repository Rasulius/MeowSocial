package com.rossgramm.rossapp.home.data.webAPI

import com.rossgramm.rossapp.home.data.models.PostListModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Header


interface GetPostListAPI {
    @GET("/Post/get-by-followings")
    suspend fun getPostsList(
        @Query("Offset") Offset: Int,  //нужно в будущем для пагинации
        @Query("Limit") Limit: Int,    //нужно в будущем для пагинации
        @Header("Authorization") Authorization: String
    ): PostListModel
}