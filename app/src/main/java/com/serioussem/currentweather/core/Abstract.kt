package com.serioussem.currentweather.core

abstract class Abstract {

    abstract class Object<T, M: Mapper>{

        abstract fun map(mapper: Mapper): T
    }

    interface Mapper{
        class Empty : Mapper
    }
}