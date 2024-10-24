package com.jogasoft.moviefinder.movie.data

data class Movie(
    val id: Int,
    val backdropPath: String?,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val category: MovieCategory
)