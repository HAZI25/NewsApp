package com.example.newsapp.di

import android.app.Application
import com.example.newsapp.presentation.ArticleFragment
import com.example.newsapp.presentation.breaking_news.BreakingNewsFragment
import com.example.newsapp.presentation.favourite_news.FavouriteNewsFragment
import com.example.newsapp.presentation.search_news.SearchNewsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: BreakingNewsFragment)
    fun inject(fragment: SearchNewsFragment)
    fun inject(fragment: ArticleFragment)
    fun inject(fragment: FavouriteNewsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}