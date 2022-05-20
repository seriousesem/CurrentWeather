package com.serioussem.currentweather.data.datasource.mappers

import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.datasource.models.DataWeatherModel
import com.serioussem.currentweather.domain.core.BaseMapper
import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.models.DomainWeatherModel
import javax.inject.Inject

class DataResultToDomainResultMapper @Inject constructor(private val modelMapper: DataModelToDomainModelMapper) :
    BaseMapper<DataResult<DataWeatherModel?>, DomainResult<DomainWeatherModel?>> {

    override fun map(data: DataResult<DataWeatherModel?>): DomainResult<DomainWeatherModel?> {
        return when (data) {
            is DataResult.Success -> DomainResult.Success(data = modelMapper.map(data.data as DataWeatherModel))
            else -> DomainResult.Error(message = data.message)
        }
    }
}
