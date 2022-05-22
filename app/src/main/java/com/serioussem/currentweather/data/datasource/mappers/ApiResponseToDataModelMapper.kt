package com.serioussem.currentweather.data.datasource.mappers

import com.serioussem.currentweather.data.datasource.models.ApiResponse
import com.serioussem.currentweather.domain.core.Mapper
import javax.inject.Inject

class ApiResponseToDataModelMapper @Inject constructor() : Mapper<ApiResponse, Double> {
    override fun map(data: ApiResponse): Double =   data.main.temperature
}