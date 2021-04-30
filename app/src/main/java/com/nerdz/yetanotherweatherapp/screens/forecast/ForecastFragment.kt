package com.nerdz.yetanotherweatherapp.screens.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nerdz.yetanotherweatherapp.databinding.FragmentForecastBinding
import com.nerdz.yetanotherweatherapp.models.Data
import com.nerdz.yetanotherweatherapp.models.Forecast
import com.nerdz.yetanotherweatherapp.network.Api
import com.nerdz.yetanotherweatherapp.network.NetworkService
import com.nerdz.yetanotherweatherapp.network.Status
import com.nerdz.yetanotherweatherapp.screens.MainRepository
import com.nerdz.yetanotherweatherapp.screens.forecast.daily_views.DailyWeatherRecyclerViewAdapter

class ForecastFragment : Fragment() {
    private val client = NetworkService.createService(Api::class.java)
    private val mainRepository = MainRepository(client)
    private val viewModel: ForecastViewModel by viewModels { ForecastViewModelFactory(mainRepository) }
    private var _viewBinding: FragmentForecastBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var dailyWeatherRecyclerViewAdapter: DailyWeatherRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentForecastBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    private fun setupUI() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = place
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        dailyWeatherRecyclerViewAdapter = DailyWeatherRecyclerViewAdapter(arrayListOf()) { hourlyData ->
            navigateToDetail(hourlyData)
        }
        viewBinding.recyclerView.adapter = dailyWeatherRecyclerViewAdapter
    }

    private fun setupObservers() {
        viewModel.getForecast(place, units, appId).observe(viewLifecycleOwner, { resource ->
            when(resource.status) {
                Status.SUCCESS -> {
                    viewBinding.progressBar.visibility = View.GONE
                    resource.data?.let {
                        viewBinding.recyclerView.visibility = View.VISIBLE
                        setDailyForecast(it)
                    }
                }
                Status.ERROR -> {
                    viewBinding.progressBar.visibility = View.GONE
                    viewBinding.recyclerView.visibility = View.GONE
                }

                Status.LOADING -> {
                    viewBinding.progressBar.visibility = View.VISIBLE
                    viewBinding.recyclerView.visibility = View.GONE
                }
            }
        })
    }

    private fun setDailyForecast(forecast: Forecast) {
        dailyWeatherRecyclerViewAdapter.apply {
            val dailyData = forecast.getGroupedData()
            setDailyData(dailyData)
            notifyDataSetChanged()
        }
    }

    private fun navigateToDetail(hourlyData: Data) {
        val action = ForecastFragmentDirections.actionForecastFragmentToDetailFragment(
            atHourData = hourlyData,
            title = hourlyData.getLocalDateTimeFormatted()
        )
        findNavController().navigate(action)
    }

    companion object {
        const val place = "Berlin"
        const val appId = "428d70aa1268b4be33b6fd9d7f12bc2c"
        const val units = "metric"
    }
}