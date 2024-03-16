package com.route.newsapp_c39_gmonthu.api

import com.route.newsapp_c39_gmonthu.model.ArticlesResponse
import com.route.newsapp_c39_gmonthu.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsServices {
    @GET("top-headlines/sources")
    fun getNewsSources(
        @Query("apiKey") apiKey: String,
        @Query("category") categoryId: String?
    ): Call<SourcesResponse>

    @GET("everything")
    fun getNewsBySource(
        @Query("apiKey") apiKey: String,
        @Query("sources") sourceId: String
    ): Call<ArticlesResponse>

    @GET("/v2/everything")
    fun getNewsItem(@Query("q")title:String,@Query("searchIn")topic:String="title",@Query("apiKey")apiKey:String):Call<ArticlesResponse>

    @GET("/v2/everything")
    fun getSearchedArticles(@Query("q")searchQuery:String,@Query("apiKey")apiKey:String):Call<ArticlesResponse>
}

