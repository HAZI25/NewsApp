package com.example.newsapp.domain.use_case

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.repository.NewsRepository
import javax.inject.Inject

class InsertUpdateArticleUseCase @Inject constructor(
    private val repository: NewsRepository,
) {
    suspend operator fun invoke(article: Article) {
        return repository.insertUpdateArticle(article)
    }
}