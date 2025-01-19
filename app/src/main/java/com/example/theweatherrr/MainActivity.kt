package com.example.theweatherrr


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.theweatherrr.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
    }

    private fun fetchWeather(city: String, tvWeatherInfo: TextView) {
        val apiKey = "090ba223af19450c0e1490d49060d1b5"  // Replace with your actual OpenWeatherMap API key

        RetrofitInstance.api.getWeather(city, apiKey).enqueue(object : Callback<WeatherResponse> {
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
}
