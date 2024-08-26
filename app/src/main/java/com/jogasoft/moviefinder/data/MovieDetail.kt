package com.jogasoft.moviefinder.data

data class MovieDetail(
    val id: Int,
    val backdropPath: String,
    val budget: Int,
    val genres: List<String>,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val runtime: Int,
    val tagline: String,
    val title: String,
)