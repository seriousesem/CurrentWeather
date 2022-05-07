package com.serioussem.currentweather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.serioussem.currentweather.core.Constants.FIRST_CITY
import com.serioussem.currentweather.core.Constants.SECOND_CITY
import com.serioussem.currentweather.core.hideView
import com.serioussem.currentweather.core.showView
import com.serioussem.currentweather.core.snackbar
import com.serioussem.currentweather.databinding.ActivityWeatherBinding
import com.serioussem.currentweather.domain.model.WeatherModel
import dagger.hilt.android.AndroidEntryPoint
import com.serioussem.currentweather.presentation.WeatherViewModel.WeatherActivityState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initObservers()
        initData()

    }

    private fun initData() {
        swipeRefresh()
    }

    private fun handleActivityState(state: WeatherActivityState) {
        when (state) {
            is WeatherActivityState.ShowSnackbar -> snackbar(state.message)
            is WeatherActivityState.Loading -> handleLoading(state.isLoading)
            is WeatherActivityState.Init -> showProgressbar()
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            showProgressbar()
        } else {
            showContent()
        }
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
        observeActivityState()
        observeWeather()
    }

    private fun observeWeather() {
        viewModel.apply {
            firstCityWeather.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach { updateView(it) }
                .launchIn(lifecycleScope)
            secondCityWeather.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach { updateView(it) }
                .launchIn(lifecycleScope)
        }

    }

    private fun updateView(weather: WeatherModel) {
        binding.apply {
            val city = weather.city
            val temperature = weather.temperature.toString()
            when (city) {
                FIRST_CITY -> {
                    firstCityName.text = city
                    textFirstCityTemperature.text = temperature
                }
                SECOND_CITY -> {
                    secondCityName.text = city
                    textSecondCityTemperature.text = temperature
                }
            }
        }
    }

    private fun observeActivityState() {
        viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { handleActivityState(it) }
            .launchIn(lifecycleScope)
    }

    private fun swipeRefresh() {
        binding.swipeRefreshContainer.setOnRefreshListener {
            viewModel.apply {
                fetchWeather()
            }
            binding.swipeRefreshContainer.isRefreshing = false
        }
    }

    private fun cleanEdit() {
        binding.editCustomCity.text.clear()
    }
}