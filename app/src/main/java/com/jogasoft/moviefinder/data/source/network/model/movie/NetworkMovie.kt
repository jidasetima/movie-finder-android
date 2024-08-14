package com.jogasoft.moviefinder.data.source.network.model.movie

import com.jogasoft.moviefinder.data.Movie
import com.squareup.moshi.Json

data class NetworkMovie(
    val id: Int,
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "genre_ids")
    val genreIds: List<Int>,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)

//Mappers
fun NetworkMovie.toMovie(): Movie {
    return Movie(
        id = id,
        backdropPath = backdropPath,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title
    )
}

fun List<NetworkMovie>.toMovies() = map(NetworkMovie::toMovie)