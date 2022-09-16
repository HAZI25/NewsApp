package com.example.newsapp.data.database.model

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.Source

fun Article.mapToDbModel(): ArticleDbModel {
    return ArticleDbModel(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source.mapToDbModel(),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

private fun Source.mapToDbModel(): SourceDbModel {
    return SourceDbModel(
        id = id,
        name = name
    )
}

fun ArticleDbModel.mapToDomain(): Article {
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

private fun SourceDbModel.mapToDomain(): Source {
    return Source(
        id = id,
        name = name
    )
}