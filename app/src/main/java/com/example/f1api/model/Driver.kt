package com.example.f1api.model

data class Driver(
    val driverId: String,
    val name: String,
    val surname: String,
    val nationality: String?,
    val birthday: String?,
    val number: Int?,
    val shortName: String?,
    val url: String?
)
