package com.serioussem.currentweather.data.datasource.mappers


import com.serioussem.currentweather.data.datasource.models.DataWeatherModel
import com.serioussem.currentweather.domain.core.BaseMapper
import com.serioussem.currentweather.domain.models.DomainWeatherModel
import javax.inject.Inject

class DataModelToDomainModelMapper @Inject constructor(): BaseMapper<DataWeatherModel?, DomainWeatherModel?> {
    override fun map(data: DataWeatherModel?): DomainWeatherModel? =
        data?.let { DomainWeatherModel(city = it.city, temperature = data.temperature) }
}