package com.jogasoft.moviefinder.data.source.network.model.movieDetail

import com.squareup.moshi.Json

data class NetworkBelongsToCollection(
    @Json(name = "backdrop_path")
    val backdropPath: String,
    val id: Int,
    val name: String,
    @Json(name = "poster_path")
    val posterPath: String
)