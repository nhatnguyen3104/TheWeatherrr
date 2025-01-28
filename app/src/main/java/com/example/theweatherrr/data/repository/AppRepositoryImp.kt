package com.example.theweatherrr.data.repository

import com.example.theweatherrr.data.model.weather.WeatherResponse
import com.example.theweatherrr.domain.repository.AppRepository
import com.example.theweatherrr.presentation.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AppRepositoryImp: AppRepository {

    private val apiService = NetworkModule.apiService

    override fun getCurrentWeather(): Flow<WeatherResponse> = flow {
        val response = apiService.getWeather("Ha Noi", "090ba223af19450c0e1490d49060d1b5", "metric")

        if (response.isSuccessful) {
            response.body()?.let { weatherResponse ->
                emit(weatherResponse)
            }
        }
    }.flowOn(Dispatchers.IO)

}