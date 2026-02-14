package com.example.f1api.model

data class Standing(
    val position: Int?,
    val points: Float?,
    val driver: DriverSummary?,
    val team: TeamSummary?
)