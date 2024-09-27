package com.jogasoft.moviefinder.data.source.network.model.movieDetail

import com.squareup.moshi.Json

data class NetworkSpokenLanguage(
    @Json(name = "english_name")
    val englishName: String,
    val iso_639_1: String,
    val name: String
)