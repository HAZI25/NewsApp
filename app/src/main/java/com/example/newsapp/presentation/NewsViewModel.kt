package com.example.newsapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.newsapp.common.likeStateFlow
import com.example.newsapp.domain.use_case.GetBreakingNewsUseCase
import com.example.newsapp.presentation.model.Article
import com.example.newsapp.presentation.model.mapToPresentation
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
) : ViewModel() {

    val breakingNews: StateFlow<PagingData<Article>> =
        getBreakingNewsUseCase().cachedIn(viewModelScope).map { pagingData ->
            pagingData.map { article ->
                article.mapToPresentation()
            }
        }.likeStateFlow(viewModelScope, PagingData.empty())
}