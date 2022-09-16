package com.example.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsapp.data.network.api.NewsApi
import com.example.newsapp.data.network.model.mapToDomain
import com.example.newsapp.data.network.paging.BrakingNewsPagingSource
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
) : NewsRepository {
    override fun getBreakingNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                BrakingNewsPagingSource(newsApi)
            }
        ).flow.map { pagingArticleDtoData ->
            pagingArticleDtoData.map { articleDto ->
                articleDto.mapToDomain()
            }
        }
    }
}