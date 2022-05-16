package com.rossgramm.rossapp.base.web

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebApi  {
    private const val  BASE_URL ="http://rossapp.ru"

    fun getRetrofit(): Retrofit {
        val httpClient = createOkHttpClient()
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(createGsonConverterFactory())
            .client(httpClient)
        return builder.build()
    }

    private fun createGsonConverterFactory(): Converter.Factory {
        val gson = GsonBuilder()
            //register adapters
            .create()
        return GsonConverterFactory.create(gson)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
            //TODO: remove in prod version

            addInterceptor(getLoggingInterceptor())
        }
        return httpClientBuilder.build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }


}