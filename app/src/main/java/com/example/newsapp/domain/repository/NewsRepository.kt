package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getBreakingNews(): Flow<PagingData<Article>>

    fun searchNews(query: String): Flow<PagingData<Article>>

    fun getSavedNews(): Flow<List<Article>>

    suspend fun insertUpdateArticle(article: Article)

    suspend fun deleteArticle(article: Article)
}