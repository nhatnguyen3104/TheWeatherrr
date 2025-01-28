package com.example.theweatherrr.data.network

import com.example.theweatherrr.data.model.weather.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" // metric for Celsius, imperial for Fahrenheit
    ): Call<WeatherResponse>
}

