package com.example.newsapp.di

import android.app.Application
import com.example.newsapp.presentation.breaking_news.BreakingNewsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: BreakingNewsFragment)
}