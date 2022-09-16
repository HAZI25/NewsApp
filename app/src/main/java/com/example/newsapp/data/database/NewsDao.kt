package com.example.newsapp.data.database

import androidx.room.*
import com.example.newsapp.data.database.model.ArticleDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateArticle(article: ArticleDbModel)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<ArticleDbModel>>

    @Delete
    suspend fun deleteArticle(article: ArticleDbModel)
}