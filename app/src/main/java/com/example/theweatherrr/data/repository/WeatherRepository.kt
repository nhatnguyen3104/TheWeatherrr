package com.example.theweatherrr.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.theweatherrr.domain.repository.WeatherResponse
import com.example.theweatherrr.data.network.WeatherApiService
import com.example.theweatherrr.model.WeatherInfo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {
    private val apiService: WeatherApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(WeatherApiService::class.java)
    }
    fun getWeather(city: String, apiKey: String): LiveData<WeatherInfo> {
        val liveData = MutableLiveData<WeatherInfo>()

        apiService.getWeather(city, apiKey).enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(
                call: retrofit2.Call<WeatherResponse>,
                response: retrofit2.Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        val weatherInfo = WeatherInfo(
                            cityName = it.name,
                            temperature = "${it.main.temp}°C",
                            weatherDescription = it.weather[0].description,
                            iconUrl = "https://openweathermap.org/img/w/${it.weather[0].icon}.png"
                        )
                        liveData.postValue(weatherInfo)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<WeatherResponse>, t: Throwable) {
                Log.e("API_ERROR", "fail call API: ${t.message}")
            }
        })

        return liveData
    }
}
