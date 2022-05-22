package com.serioussem.currentweather.data.datasource.mappers

import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.datasource.models.DataWeatherModel
import com.serioussem.currentweather.domain.core.Mapper
import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.models.DomainModel
import javax.inject.Inject

class DataResultToDomainResultMapper @Inject constructor(private val modelMapper: DataModelToDomainModelMapper) :
    Mapper<DataResult<DataWeatherModel?>, DomainResult<DomainModel?>> {

    override fun map(data: DataResult<DataWeatherModel?>): DomainResult<DomainModel?> {
        return when (data) {
            is DataResult.Success -> DomainResult.Success(data = modelMapper.map(data.data as DataWeatherModel))
            else -> DomainResult.Error(message = data.message)
        }
    }
}
