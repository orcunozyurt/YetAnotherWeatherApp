package com.nerdz.yetanotherweatherapp.screens.forecast.hourly_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nerdz.yetanotherweatherapp.databinding.ListItemHourlyForecastBinding
import com.nerdz.yetanotherweatherapp.models.Data
import com.nerdz.yetanotherweatherapp.screens.forecast.daily_views.HourlyViewClickListener


class HourlyWeatherRecyclerViewAdapter(
    private val data: MutableList<Data>,
    private val hourlyViewClickListener: HourlyViewClickListener) : RecyclerView.Adapter<HourlyWeatherRecyclerViewAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ListItemHourlyForecastBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Data) {
            val localDateTime = data.getLocalDateTime()
            binding.timeView.text = localDateTime.toLocalTime().toString()
            binding.temperatureView.text = data.getFormattedTemp()
            val iconURL = "https://openweathermap.org/img/wn/${data.weather.first().icon}.png"
            Glide.with(itemView.context)
                .load(iconURL)
                .into(binding.weatherIcon)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ListItemHourlyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = DataViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            hourlyViewClickListener(data[position])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setData(data: List<Data>) {
        this.data.apply {
            clear()
            addAll(data)
        }

    }
}