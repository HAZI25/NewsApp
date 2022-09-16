package com.example.newsapp.data.network.model

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Source

fun ArticleDto.mapToDomain(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source.mapToDomain(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun SourceDto.mapToDomain(): Source {
    return Source(
        id = id,
        name = name
    )
}