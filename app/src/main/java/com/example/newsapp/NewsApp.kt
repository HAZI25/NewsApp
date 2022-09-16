package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.ApplicationComponent
import com.example.newsapp.di.DaggerApplicationComponent

class NewsApp : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.create()
    }
}