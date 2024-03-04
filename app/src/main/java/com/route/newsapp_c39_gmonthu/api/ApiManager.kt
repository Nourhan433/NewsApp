package com.route.newsapp_c39_gmonthu.api

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getNewsServices(): NewsServices {
        return retrofit.create(NewsServices::class.java)
    }
}
