package com.route.newsapp_c39_gmonthu.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    private val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
        Log.e("API", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .client(okhttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getNewsServices(): NewsServices {
        return retrofit.create(NewsServices::class.java)
    }
}
