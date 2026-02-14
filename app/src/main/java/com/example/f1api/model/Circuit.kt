package com.example.f1api.model

data class Circuit(
    val circuitId: String,
    val circuitName: String,
    val country: String?,
    val city: String?,
    val circuitLength: Int?,
    val lapRecord: String?,
    val firstParticipationYear: Int?,
    val numberOfCorners: Int?,
    val fastestLapDriverId: String?,
    val fastestLapTeamId: String?,
    val fastestLapYear: Int?,
    val url: String?
)
