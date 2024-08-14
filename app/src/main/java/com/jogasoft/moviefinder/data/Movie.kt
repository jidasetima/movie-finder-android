package com.jogasoft.moviefinder.data

data class Movie(
    val id: Int,
    val backdropPath: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
)