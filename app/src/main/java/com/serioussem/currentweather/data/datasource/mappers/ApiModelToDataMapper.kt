package com.serioussem.currentweather.data.datasource.mappers

import com.serioussem.currentweather.data.datasource.models.ApiModel
import com.serioussem.currentweather.domain.core.Mapper
import javax.inject.Inject

class ApiModelToDataMapper @Inject constructor() : Mapper<ApiModel, Double> {
    override fun map(params: ApiModel): Double =   params.main.temperature
}