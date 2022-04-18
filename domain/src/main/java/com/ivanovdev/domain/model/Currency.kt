package com.ivanovdev.domain.model

data class Currency(
    val name: String = "",
    var value: Double = 0.0,
    var isFavorite: Boolean = false
)
