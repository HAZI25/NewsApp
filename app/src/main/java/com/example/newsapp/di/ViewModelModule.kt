package com.example.newsapp.di

import androidx.lifecycle.ViewModel
import com.example.newsapp.presentation.NewsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @ViewModelKey(NewsViewModel::class)
    @IntoMap
    fun bindCoinViewModel(viewModel: NewsViewModel): ViewModel
}