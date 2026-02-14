package com.example.f1api.repository

import com.example.f1api.model.*
import com.example.f1api.network.RetrofitClient
import retrofit2.Response

class F1Repository {

    // Drivers
    suspend fun getDrivers(): Response<DriversResponse> =
        RetrofitClient.apiService.getDrivers()

    // Teams
    suspend fun getTeams(): Response<TeamsResponse> =
        RetrofitClient.apiService.getTeams()

    // Seasons
    suspend fun getSeasons(): Response<SeasonsResponse> =
        RetrofitClient.apiService.getSeasons()

    // Circuits
    suspend fun getCircuits(): Response<CircuitsResponse> =
        RetrofitClient.apiService.getCircuits()

    // Races por año
    suspend fun getRacesByYear(year: Int): Response<RacesResponse> =
        RetrofitClient.apiService.getRacesByYear(year)

    // Resultados FP1 de una carrera
    suspend fun getFP1(year: Int, round: Int): Response<ResultsResponse> =
        RetrofitClient.apiService.getFP1(year, round)

    // Clasificación de pilotos por año
    suspend fun getDriversChampionship(year: Int): Response<StandingsResponse> =
        RetrofitClient.apiService.getDriversChampionship(year)
}
