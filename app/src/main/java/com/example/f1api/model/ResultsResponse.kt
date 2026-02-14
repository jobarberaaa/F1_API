package com.example.f1api.model

data class ResultsResponse(
    val season: Int?,
    val races: RaceResults?
)

data class RaceResults(
    val round: String?,
    val fp1Results: List<Result>?
)