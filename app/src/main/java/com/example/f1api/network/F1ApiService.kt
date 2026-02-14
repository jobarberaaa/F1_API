package com.example.f1api.network

import com.example.f1api.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface F1ApiService {

    @GET("api/drivers")
    suspend fun getDrivers(): Response<DriversResponse>

    @GET("api/teams")
    suspend fun getTeams(): Response<TeamsResponse>

    @GET("api/seasons")
    suspend fun getSeasons(): Response<SeasonsResponse>

    @GET("api/circuits")
    suspend fun getCircuits(): Response<CircuitsResponse>

    @GET("api/{year}")
    suspend fun getRacesByYear(@Path("year") year: Int): Response<RacesResponse>

    @GET("api/{year}/{round}/fp1")
    suspend fun getFP1(
        @Path("year") year: Int,
        @Path("round") round: Int
    ): Response<ResultsResponse>

    @GET("api/{year}/drivers-championship")
    suspend fun getDriversChampionship(@Path("year") year: Int): Response<StandingsResponse>
}
