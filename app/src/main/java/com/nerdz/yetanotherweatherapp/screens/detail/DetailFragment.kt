package com.nerdz.yetanotherweatherapp.screens.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nerdz.yetanotherweatherapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _viewBinding: FragmentDetailBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentDetailBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = args.atHourData
        viewBinding.temperatureView.text = data.getFormattedTemp()
        viewBinding.feelsLikeView.text = data.getFormattedFeel()
        viewBinding.pressureView.text = data.main.pressure.toString()
        viewBinding.humidityView.text = data.main.humidity.toString()
        viewBinding.weatherTitle.text = data.weather[0].main
        viewBinding.weatherDesc.text = data.weather[0].description
        Glide.with(this).load(data.getIconURL()).into(viewBinding.weatherIcon)
        viewBinding.windSpeedView.text = data.wind.speed.toString()
        viewBinding.windDegreeView.text = data.wind.deg.toString()
        viewBinding.interpretationView.text = data.getInterpretation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}