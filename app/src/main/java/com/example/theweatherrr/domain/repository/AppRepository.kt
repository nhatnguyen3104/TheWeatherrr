package com.example.theweatherrr.domain.repository

import com.example.theweatherrr.data.model.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getCurrentWeather(): Flow<WeatherResponse>
}