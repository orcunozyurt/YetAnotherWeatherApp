package com.nerdz.yetanotherweatherapp.screens.forecast.daily_views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nerdz.yetanotherweatherapp.databinding.ListItemDailyForecastBinding
import com.nerdz.yetanotherweatherapp.models.DailyData
import com.nerdz.yetanotherweatherapp.models.Data
import com.nerdz.yetanotherweatherapp.screens.forecast.hourly_views.HourlyWeatherRecyclerViewAdapter

typealias HourlyViewClickListener = (data: Data) -> Unit

class DailyWeatherRecyclerViewAdapter(
    private val dailyDataList: MutableList<DailyData>,
    private val hourlyViewClickListener: HourlyViewClickListener
    ) : RecyclerView.Adapter<DailyWeatherRecyclerViewAdapter.DataViewHolder>() {

    class DataViewHolder(
        private val binding: ListItemDailyForecastBinding,
        private val hourlyViewClickListener: HourlyViewClickListener
        ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dailyData: DailyData) {
            binding.dayNameView.text = dailyData.dailydata.first.dayOfWeek.name
            binding.dateView.text = dailyData.dailydata.first.toString()
            binding.maxTempView.text = dailyData.getMaxTemp()
            binding.minTempView.text = dailyData.getMinTemp()

            binding.hourlyRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            val hourlyWeatherRecyclerViewAdapter = HourlyWeatherRecyclerViewAdapter(
                dailyData.dailydata.second.toMutableList(),
                hourlyViewClickListener
            )
            binding.hourlyRecyclerView.adapter = hourlyWeatherRecyclerViewAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            ListItemDailyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding, hourlyViewClickListener)
    }

    override fun getItemCount(): Int = dailyDataList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dailyDataList[position])
    }

    fun setDailyData(items: List<DailyData>) {
        this.dailyDataList.apply {
            clear()
            addAll(items)
        }
    }
}