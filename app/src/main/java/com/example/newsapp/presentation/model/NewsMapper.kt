package com.example.newsapp.presentation.model

import com.example.newsapp.domain.model.Article as ArticleDomain
import com.example.newsapp.domain.model.Source as SourceDomain
import com.example.newsapp.presentation.model.Article as ArticlePresentation
import com.example.newsapp.presentation.model.Source as SourcePresentation

fun ArticleDomain.mapToPresentation(): ArticlePresentation {
    return ArticlePresentation(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source.mapToPresentation(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun SourceDomain.mapToPresentation(): SourcePresentation {
    return SourcePresentation(
        id = id,
        name = name
    )
}