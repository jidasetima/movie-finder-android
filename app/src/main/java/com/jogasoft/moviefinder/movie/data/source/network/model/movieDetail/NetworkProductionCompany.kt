package com.jogasoft.moviefinder.movie.data.source.network.model.movieDetail

import com.squareup.moshi.Json

data class NetworkProductionCompany(
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String?,
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String
)