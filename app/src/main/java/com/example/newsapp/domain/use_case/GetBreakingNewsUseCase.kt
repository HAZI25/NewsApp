package com.example.newsapp.domain.use_case

import androidx.paging.PagingData
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreakingNewsUseCase @Inject constructor(
    private val repository: NewsRepository,
) {
    operator fun invoke(): Flow<PagingData<Article>> {
        return repository.getBreakingNews()
    }
}