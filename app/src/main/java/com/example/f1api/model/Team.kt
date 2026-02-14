package com.example.f1api.model

import com.google.gson.annotations.SerializedName

data class Team(
    val teamId: String,
    @SerializedName("teamName") val name: String,
    @SerializedName("teamNationality") val nationality: String?,
    val url: String?
)
