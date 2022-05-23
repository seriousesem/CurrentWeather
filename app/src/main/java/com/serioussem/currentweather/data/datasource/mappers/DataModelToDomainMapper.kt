package com.serioussem.currentweather.data.datasource.mappers


import com.serioussem.currentweather.data.datasource.models.DataModel
import com.serioussem.currentweather.domain.core.Mapper
import com.serioussem.currentweather.domain.models.DomainModel
import javax.inject.Inject

class DataModelToDomainMapper @Inject constructor(): Mapper<DataModel, DomainModel> {
    override fun map(params: DataModel): DomainModel =
        DomainModel(city = params.city, temperature = params.temperature)
}