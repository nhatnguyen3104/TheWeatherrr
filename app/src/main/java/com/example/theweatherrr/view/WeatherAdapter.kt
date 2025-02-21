package com.example.theweatherrr.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.theweatherrr.databinding.MenuItemBinding
import com.example.theweatherrr.model.WeatherInfo

class WeatherAdapter(private var weatherList: List<WeatherInfo>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private var filteredList: List<WeatherInfo> = weatherList

    inner class WeatherViewHolder(val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = filteredList[position]
        with(holder.binding) {
            tvAddress.text = weather.cityName
            tvCloud.text = weather.weatherDescription
            tvTemperature.text = weather.temperature
            Glide.with(root.context)
                .load(weather.iconUrl)
                .into(imgCloud)
        }
    }

    override fun getItemCount(): Int = filteredList.size

    fun updateData(newList: List<WeatherInfo>) {
        weatherList = newList
        filteredList = newList
        notifyDataSetChanged()
    }
    fun filterList(query: String) {
        filteredList = if (query.isEmpty()) {
            weatherList
        } else {
            weatherList.filter { it.cityName.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
    }
}
