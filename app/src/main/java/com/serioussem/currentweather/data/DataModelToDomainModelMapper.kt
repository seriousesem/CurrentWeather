package com.serioussem.currentweather.data

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.net.ServerModel
import com.serioussem.currentweather.domain.DomainModel
import retrofit2.HttpException
import java.net.UnknownHostException

interface DataModelToDomainModelMapper : Abstract.Mapper {

    fun map(data: ServerModel): DomainModel

    fun map(e: Exception): DomainModel

    class Base : DataModelToDomainModelMapper {
        override fun map(temperature: ServerModel): DomainModel =
            DomainModel.Success(temperature = temperature)

        override fun map(error: Exception): DomainModel {
            val errorType = when (error) {
                is UnknownHostException -> 0 // enum class ErrorType -  NoConnection
                is HttpException -> 1 // ServiceUnavailable
                else -> 2 // GenericException
            }
            return DomainModel.Failure(errorType)
        }

    }

}

