package com.example.theweatherrr.presentation.ui


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theweatherrr.databinding.ActivityMainBinding
import com.example.theweatherrr.view.WeatherAdapter
import com.example.theweatherrr.viewModel.WeatherViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weatherAdapter = WeatherAdapter(emptyList())
        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        binding.rvSearch.adapter= weatherAdapter

        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        val apiKey = "090ba223af19450c0e1490d49060d1b5" //key
        weatherViewModel.fetchWeatherData(apiKey)

        weatherViewModel.weatherData.observe(this) { weatherList ->
            weatherList?.let {
                weatherAdapter.updateData(it)
            }
        }

        binding.edtCityName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                weatherAdapter.filterList(s.toString())
            }
        })
    }
}
