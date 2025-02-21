package com.example.theweatherrr.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theweatherrr.data.repository.WeatherRepository
import com.example.theweatherrr.model.WeatherInfo

class WeatherViewModel: ViewModel() {
    private val repository = WeatherRepository()

    private val _weatherData = MutableLiveData<List<WeatherInfo>>()
    val weatherData: LiveData<List<WeatherInfo>> get() = _weatherData

    fun fetchWeatherData(apiKey: String) {
        val cities = listOf("Hanoi", "Ho Chi Minh", "Da Nang","Nam Dinh")

        val weatherList = mutableListOf<WeatherInfo>()
        cities.forEach { city ->
            repository.getWeather(city, apiKey).observeForever { weatherInfo ->
                weatherList.add(weatherInfo)
                _weatherData.postValue(weatherList)
            }
        }
    }
}