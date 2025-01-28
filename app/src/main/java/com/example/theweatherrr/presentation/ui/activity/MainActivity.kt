package com.example.theweatherrr.presentation.ui.activity


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.lifecycleScope
import com.example.theweatherrr.R
import com.example.theweatherrr.presentation.di.NetworkModule
import com.example.theweatherrr.data.model.weather.WeatherResponse
import com.example.theweatherrr.databinding.ActivityMainBinding
import com.example.theweatherrr.presentation.di.ViewModelModule
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = ViewModelModule.mainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val etCityName = findViewById<EditText>(R.id.etCityName)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val tvWeatherInfo = findViewById<TextView>(R.id.tvWeatherInfo)

        btnSearch.setOnClickListener {
            val cityName = etCityName.text.toString().trim()

            if (cityName.isEmpty()) {
                Toast.makeText(this, "Please enter a city name: ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            fetchWeather(cityName, tvWeatherInfo)
        }

        // get current weather demo
        viewModel.fetchWeather()

        setUpObserver()
    }

    private fun fetchWeather(city: String, tvWeatherInfo: TextView) {
        val apiKey = "090ba223af19450c0e1490d49060d1b5"  // Replace with your actual OpenWeatherMap API key

        NetworkModule.api.getWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    weatherResponse?.let {
                        val arr = """
                            City: ${it.name}
                            Temp: ${it.main.temp}°C
                            Weather: ${it.weather[0].description}
                            Humidity: ${it.main.humidity}%
                        """.trimIndent()

                        tvWeatherInfo.text = arr
                    }
                } else {
                    tvWeatherInfo.text = "Not found"
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                tvWeatherInfo.text = "Error: ${t.message}"
            }
        })
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                Log.d("MainActivity", "Weather Information: ${state?.weather}")
            }
        }
    }
}
