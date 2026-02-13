package com.example.f1api.repository

import com.example.f1api.model.*
import com.example.f1api.network.RetrofitClient
import retrofit2.Response

class F1Repository {

    // Drivers
    suspend fun getDrivers(): Response<DriversResponse> = RetrofitClient.api.getDrivers()
    suspend fun getDriverDetail(driverId: String): Response<Driver> = RetrofitClient.api.getDriverDetail(driverId)

    // Teams
    suspend fun getTeams(): Response<TeamsResponse> = RetrofitClient.api.getTeams()
    suspend fun getTeamDetail(teamId: String): Response<Team> = RetrofitClient.api.getTeamDetail(teamId)

    // Seasons
    suspend fun getSeasons(): Response<SeasonsResponse> = RetrofitClient.api.getSeasons()
    suspend fun getSeasonDetail(year: Int): Response<Season> = RetrofitClient.api.getSeasonDetail(year)

    // Results
    suspend fun getResults(): Response<ResultsResponse> = RetrofitClient.api.getResults()
    suspend fun getResultDetail(raceId: String): Response<Result> = RetrofitClient.api.getResultDetail(raceId)

    // Standings
    suspend fun getStandings(): Response<StandingsResponse> = RetrofitClient.api.getStandings()
    suspend fun getStandingsBySeason(season: Int): Response<StandingsResponse> = RetrofitClient.api.getStandingsBySeason(season)

    // Circuits
    suspend fun getCircuits(): Response<CircuitsResponse> = RetrofitClient.api.getCircuits()
    suspend fun getCircuitDetail(circuitId: String): Response<Circuit> = RetrofitClient.api.getCircuitDetail(circuitId)

    // Races
    suspend fun getRaces(): Response<RacesResponse> = RetrofitClient.api.getRaces()
    suspend fun getRaceDetail(raceId: String): Response<Race> = RetrofitClient.api.getRaceDetail(raceId)
}
