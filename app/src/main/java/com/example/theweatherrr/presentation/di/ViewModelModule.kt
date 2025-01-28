package com.example.theweatherrr.presentation.di

import com.example.theweatherrr.presentation.ui.activity.MainViewModel

object ViewModelModule {

    val mainViewModel by lazy {
        MainViewModel()
    }
}