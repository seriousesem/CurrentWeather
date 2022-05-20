package com.serioussem.currentweather.presentation


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.utils.Constants.FIRST_CITY
import com.serioussem.currentweather.utils.Constants.SECOND_CITY
import com.serioussem.currentweather.utils.*
import com.serioussem.currentweather.databinding.ActivityWeatherBinding
import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.models.DomainWeatherModel
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
                        saveUserCity(userCity)
                        fetchWeather()
                    }
                    cleanEdit()
                } else snackbar(resourceProvider.string(R.string.enter_your_sity_name))
            }
        }
    }

    private fun initData() = swipeRefresh()

    private fun showContent() {
        hideView(binding.progressBar)
        showView(binding.contentContainer)
    }

    private fun showProgressbar() {
        showView(binding.progressBar)
        hideView(binding.contentContainer)
    }

    private fun initObservers() {
        viewModel.citiesWeather.observe(this@WeatherActivity) { resultStateList ->
            resultStateList.forEach { resultState ->
                when (resultState) {
                    is DomainResult.Init -> showProgressbar()

                    is DomainResult.Loading -> showProgressbar()

                    is DomainResult.Success<*> -> {
                        showContent()
                        resultState.data?.let { data -> updateView(data) }
                    }
                    is DomainResult.Error -> {
                        showContent()
                        resultState.message?.let { message -> snackbar(message) }
                    }
                   
                }
            }
        }
    }

    private fun updateView(weather: DomainWeatherModel) {
        binding.apply {
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

    private fun swipeRefresh() {
        binding.swipeRefreshContainer.setOnRefreshListener {
            viewModel.fetchWeather()
            binding.swipeRefreshContainer.isRefreshing = false
        }
    }

    private fun cleanEdit() = binding.editUserCity.text.clear()

    private fun updateTextView(temperature: Double): String =
        "$temperature ${resourceProvider.string(R.string.celsius)}"

}