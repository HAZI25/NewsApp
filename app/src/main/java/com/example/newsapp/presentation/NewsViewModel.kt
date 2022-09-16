package com.example.newsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.newsapp.common.likeStateFlow
import com.example.newsapp.domain.use_case.*
import com.example.newsapp.presentation.model.Article
import com.example.newsapp.presentation.model.mapToDomain
import com.example.newsapp.presentation.model.mapToPresentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModel @Inject constructor(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val searchNewsUseCase: SearchNewsUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val insertUpdateArticleUseCase: InsertUpdateArticleUseCase,
) : ViewModel() {

    val breakingNews: StateFlow<PagingData<Article>> =
        getBreakingNewsUseCase().cachedIn(viewModelScope).map { pagingData ->
            pagingData.map { article ->
                article.mapToPresentation()
            }
        }.likeStateFlow(viewModelScope, PagingData.empty())

    private val newsQuery = MutableStateFlow("")

    val newsResult = newsQuery.flatMapLatest {
        searchNewsUseCase(it).cachedIn(viewModelScope)
    }.map { pagingData ->
        pagingData.map { article ->
            article.mapToPresentation()
        }
    }.likeStateFlow(viewModelScope, PagingData.empty())

    val favouriteNews = getSavedNewsUseCase().map { listArticlesDomain ->
        listArticlesDomain.map { articleDomain ->
            articleDomain.mapToPresentation()
        }
    }.likeStateFlow(viewModelScope, emptyList())

    fun searchNews(query: String) {
        newsQuery.value = query
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            deleteArticleUseCase(article.mapToDomain())
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            insertUpdateArticleUseCase(article.mapToDomain())
        }
    }
}