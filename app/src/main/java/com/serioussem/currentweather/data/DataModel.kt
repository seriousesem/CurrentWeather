package com.serioussem.currentweather.data

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.net.ServerModel
import com.serioussem.currentweather.domain.DomainModel

sealed class DataModel : Abstract.Object<DomainModel, DataModelToDomainModelMapper>() {

    class Success(private val temperature: ServerModel) : DataModel() {
        override fun map(mapper: DataModelToDomainModelMapper): DomainModel = mapper.map(temperature)
    }
    class Failure(private val error: Exception): DataModel(){
        override fun map(mapper: DataModelToDomainModelMapper): DomainModel = mapper.map(error)
    }
}