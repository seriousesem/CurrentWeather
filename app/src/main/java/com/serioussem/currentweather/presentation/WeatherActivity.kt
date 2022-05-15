package com.serioussem.currentweather.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.serioussem.currentweather.R
import com.serioussem.currentweather.core.Constants.FIRST_CITY
import com.serioussem.currentweather.core.Constants.SECOND_CITY
import com.serioussem.currentweather.core.hideView
import com.serioussem.currentweather.core.showView
import com.serioussem.currentweather.core.snackbar
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.databinding.ActivityWeatherBinding
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.WeatherModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityWeatherBinding.inflate(layoutInflater)
    }
    private val resourceProvider by lazy(LazyThreadSafetyMode.NONE) {
        ResourceProvider(this@WeatherActivity)
    }
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initData()
        initObservers()

    }

    private fun initView() {
        binding.apply {
            btnUpdateUserCity.setOnClickListener {
                val userCity = editUserCity.text.toString()
                if (userCity.isNotEmpty()) {
                    viewModel.apply {
                        editUserCity(userCity)
                        fetchCitiesWeather()
                    }
                    cleanEdit()
                } else snackbar(resourceProvider.string(R.string.enter_your_sity_name))
            }
        }
    }

    private fun initData() {
        swipeRefresh()

    }


    private fun showContent() {
        hideView(binding.progressBar)
        showView(binding.contentContainer)
    }

    private fun showProgressbar() {
        showView(binding.progressBar)
        hideView(binding.contentContainer)
    }

    private fun initObservers() {
        viewModel.apply {
            citiesWeather.observe(this@WeatherActivity) {
                when (it) {
                    is ResultState.Init -> {
                        showProgressbar()
                    }
                    is ResultState.Loading -> {
                        showProgressbar()
                    }
                    is ResultState.Success<*> -> {
                        showContent()
                        it.data?.let { data -> updateView(data) }
                    }
                    is ResultState.Error -> {
                        showContent()
                        it.data?.let { data -> updateView(data) }
                        it.message?.let { message -> snackbar(message) }
                    }
                }
            }
        }

    }

    private fun updateView(weatherList: MutableList<WeatherModel>) {
        binding.apply {
            weatherList.forEach { weather ->
                val city = weather.city
                val temperature = weather.temperature
                when (city) {
                    FIRST_CITY -> {
                        firstCityName.text = city
                        textFirstCityTemperature.text = updateTextView(temperature = temperature)
                    }
                    SECOND_CITY -> {
                        secondCityName.text = city
                        textSecondCityTemperature.text = updateTextView(temperature = temperature)
                    }
                    city -> {
                        textUserCity.text = city
                        textUserCityTemperature.text = updateTextView(temperature = temperature)
                    }
                }
            }
        }
    }

    private fun swipeRefresh() {
        binding.swipeRefreshContainer.setOnRefreshListener {
            viewModel.apply {
                fetchCitiesWeather()
            }
            binding.swipeRefreshContainer.isRefreshing = false
        }
    }

    private fun cleanEdit() {
        binding.editUserCity.text.clear()
    }

    @SuppressLint("SetTextI18n")
    private fun updateTextView(temperature: Double): String = "$temperature °C"

}