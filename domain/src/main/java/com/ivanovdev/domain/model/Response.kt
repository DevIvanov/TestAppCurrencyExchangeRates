package com.ivanovdev.domain.model

data class Response (
    val success : Boolean,
    val timestamp : Int,
    val base : String,
    val date : String,
    val rates: Map<String, Double>
){
    fun mapToList(): List<Currency> {
        val list = mutableListOf<Currency>()
        for(i in rates) { list.add(Currency(name = i.key, value = i.value)) }
        return list
    }
}
