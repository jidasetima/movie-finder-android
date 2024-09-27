package com.jogasoft.moviefinder.data.source.network.model.movie

import com.squareup.moshi.Json

data class NetworkMoviePage(
    val page: Int,
    val results: List<NetworkMovie>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)