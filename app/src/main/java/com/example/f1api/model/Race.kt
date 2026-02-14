package com.example.f1api.model

data class Race(
    val raceId: String,
    val championshipId: String?,
    val raceName: String?,
    val circuit: CircuitSummary?,
    val date: String?,
    val round: Int?,
    val winner: DriverSummary?,
    val teamWinner: TeamSummary?
)

data class CircuitSummary(
    val circuitId: String?,
    val circuitName: String?
)

data class DriverSummary(
    val driverId: String?,
    val name: String?,
    val surname: String?
)

data class TeamSummary(
    val teamId: String?,
    val teamName: String?
)
