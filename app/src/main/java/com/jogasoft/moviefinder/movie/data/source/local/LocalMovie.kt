package com.jogasoft.moviefinder.data.source.local

import androidx.room.Entity
import com.jogasoft.moviefinder.data.Movie
import com.jogasoft.moviefinder.data.MovieCategory

@Entity(
    tableName = "movie",
    primaryKeys = ["id", "category"]
)
data class LocalMovie(
    val id: Int,
    val backdropPath: String?,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val category: MovieCategory
)

//Mappers
fun LocalMovie.toMovie(): Movie {
    return Movie(
        id = this.id,
        category = this.category,
        backdropPath = this.backdropPath,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title
    )
}

fun List<LocalMovie>.toMovies() = map(LocalMovie::toMovie)