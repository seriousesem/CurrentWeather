package com.serioussem.currentweather.data.net

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.DataModel

interface ServerModelToDataModelMapper: Abstract.Mapper{

    fun map(temperature: Double): DataModel

}

