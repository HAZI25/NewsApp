package com.example.newsapp.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.network.api.NewsApi
import com.example.newsapp.data.network.model.ArticleDto
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class BrakingNewsPagingSource(
    private val newsApi: NewsApi,
) : PagingSource<Int, ArticleDto>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {

        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = newsApi.getBreakingNews(page = position, pageSize = params.loadSize)
            val news = response.articles

            val prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1
            val nextKey = if (news.isEmpty()) null else position + 1

            LoadResult.Page(news, prevKey, nextKey)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}