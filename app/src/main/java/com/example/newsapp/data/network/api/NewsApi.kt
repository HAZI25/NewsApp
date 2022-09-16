package com.example.newsapp.data.network.api

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.network.model.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    @Headers("Authorization: $API_KEY")
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query(QUERY_PARAM_COUNTRY) countryCode: String = "us",
        @Query(QUERY_PARAM_PAGE) page: Int,
        @Query(QUERY_PARAM_PAGE_SIZE) pageSize: Int,
    ): NewsResponseDto

    @Headers("Authorization: $API_KEY")
    @GET("v2/everything")
    suspend fun searchNews(
        @Query(QUERY_PARAM_QUERY) query: String,
        @Query(QUERY_PARAM_PAGE) page: Int,
        @Query(QUERY_PARAM_PAGE_SIZE) pageSize: Int,
    ): NewsResponseDto

    companion object {
        private const val QUERY_PARAM_COUNTRY = "country"
        private const val QUERY_PARAM_QUERY = "q"
        private const val QUERY_PARAM_PAGE = "page"
        private const val QUERY_PARAM_PAGE_SIZE = "pageSize"

        private const val API_KEY = BuildConfig.API_KEY
    }
}