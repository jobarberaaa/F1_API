package com.example.f1api.model

data class Result(
    val raceId: String,
    val season: Int?,
    val round: Int?,
    val winner: Driver?,
    val team: Team?,
    val laps: Int?,
    val time: String?
)
