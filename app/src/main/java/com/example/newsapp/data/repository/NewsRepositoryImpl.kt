package com.example.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsapp.data.database.NewsDao
import com.example.newsapp.data.database.model.mapToDbModel
import com.example.newsapp.data.database.model.mapToDomain
import com.example.newsapp.data.network.api.NewsApi
import com.example.newsapp.data.network.model.mapToDomain
import com.example.newsapp.data.network.paging.BrakingNewsPagingSource
import com.example.newsapp.data.network.paging.SearchNewsPagingSource
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val dao: NewsDao,
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

    override fun searchNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchNewsPagingSource(newsApi, query)
            }
        ).flow.map { pagingData ->
            pagingData.map { articleDto ->
                articleDto.mapToDomain()
            }
        }
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return dao.getAllArticles().map { listArticles ->
            listArticles.map { articleDbModel ->
                articleDbModel.mapToDomain()
            }
        }
    }

    override suspend fun insertUpdateArticle(article: Article) {
        dao.insertUpdateArticle(article.mapToDbModel())
    }

    override suspend fun deleteArticle(article: Article) {
        dao.deleteArticle(article.mapToDbModel())
    }
}