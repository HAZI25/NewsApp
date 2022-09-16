package com.example.newsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.newsapp.common.likeStateFlow
import com.example.newsapp.domain.use_case.GetBreakingNewsUseCase
import com.example.newsapp.domain.use_case.SearchNewsUseCase
import com.example.newsapp.presentation.model.Article
import com.example.newsapp.presentation.model.mapToPresentation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModel @Inject constructor(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val searchNewsUseCase: SearchNewsUseCase,
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

    fun searchNews(query: String) {
        newsQuery.value = query
    }
}