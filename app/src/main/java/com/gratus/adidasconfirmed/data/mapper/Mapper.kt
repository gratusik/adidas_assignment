package com.gratus.adidasconfirmed.data.mapper

// mapper to interface to map to object class
interface Mapper<E, D> {

    fun mapFromEntity(type: E): D

    fun mapToEntity(type: D): E
}
