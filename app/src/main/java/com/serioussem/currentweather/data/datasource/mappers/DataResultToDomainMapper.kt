package com.serioussem.currentweather.data.datasource.mappers

import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.datasource.models.DataModel
import com.serioussem.currentweather.domain.core.Mapper
import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.models.DomainModel
import javax.inject.Inject

class DataResultToDomainMapper @Inject constructor(private val mapper: DataModelToDomainMapper) :
    Mapper<DataResult<DataModel>, DomainResult<DomainModel>> {

    override fun map(params: DataResult<DataModel>): DomainResult<DomainModel> =
        when (params) {
            is DataResult.Success -> DomainResult.Success(data = mapper.map(params.data as DataModel))
            else -> DomainResult.Error(message = params.message as String)
        }
}
