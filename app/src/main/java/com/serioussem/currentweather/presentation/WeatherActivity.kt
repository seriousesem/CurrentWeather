package com.serioussem.currentweather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.serioussem.currentweather.R
import com.serioussem.currentweather.core.Constants.FIRST_CITY
import com.serioussem.currentweather.core.Constants.SECOND_CITY
import com.serioussem.currentweather.core.hideView
import com.serioussem.currentweather.core.showView
import com.serioussem.currentweather.core.snackbar
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.databinding.ActivityWeatherBinding
import com.serioussem.currentweather.domain.model.WeatherModel
import dagger.hilt.android.AndroidEntryPoint
import com.serioussem.currentweather.presentation.WeatherViewModel.WeatherActivityState

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
            citiesWeather.observe(this@WeatherActivity) {
                updateView(it)
            }
        }
    }


    private fun updateView(citiesWeather: List<WeatherModel>) {
        binding.apply {
            citiesWeather.forEach {
                val city = it.city
                val temperature = it.temperature.toString()

                when (city) {
                    FIRST_CITY -> {
                        firstCityName.text = city
                        textFirstCityTemperature.text = temperature
                    }
                    SECOND_CITY -> {
                        secondCityName.text = city
                        textSecondCityTemperature.text = temperature
                    }
                    city -> {
                        textUserCity.text = city
                        textUserCityTemperature.text = temperature
                    }
                }
            }
        }

    }

    private fun observeActivityState() {
        viewModel.state.observe(this@WeatherActivity) {
            handleActivityState(it)
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
}