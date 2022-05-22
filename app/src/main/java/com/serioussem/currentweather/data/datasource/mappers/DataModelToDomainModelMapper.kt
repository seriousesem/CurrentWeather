package com.serioussem.currentweather.data.datasource.mappers


import com.serioussem.currentweather.data.datasource.models.DataWeatherModel
import com.serioussem.currentweather.domain.core.Mapper
import com.serioussem.currentweather.domain.models.DomainModel
import javax.inject.Inject

class DataModelToDomainModelMapper @Inject constructor(): Mapper<DataWeatherModel?, DomainModel?> {
    override fun map(data: DataWeatherModel?): DomainModel? =
        data?.let { DomainModel(city = it.city, temperature = data.temperature) }
}