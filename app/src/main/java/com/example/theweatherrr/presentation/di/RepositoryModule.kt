package com.example.theweatherrr.presentation.di

import com.example.theweatherrr.data.repository.AppRepositoryImp

object RepositoryModule {

    val appRepository by lazy {
        AppRepositoryImp()
    }
}