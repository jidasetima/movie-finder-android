package com.jogasoft.moviefinder.data.source.network.model.movieDetail

import com.jogasoft.moviefinder.data.MovieDetail
import com.squareup.moshi.Json

data class NetworkMovieDetail(
    val id: Int,
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: NetworkBelongsToCollection?,
    val budget: Int,
    val genres: List<NetworkGenre>,
    val homepage: String,
    @Json(name = "imdb_id")
    val imdbId: String,
    @Json(name = "origin_country")
    val originCountry: List<String>,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "production_companies")
    val productionCompanies: List<NetworkProductionCompany>,
    @Json(name = "production_countries")
    val productionCountries: List<NetworkProductionCountry>,
    @Json(name = "release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<NetworkSpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
)

fun NetworkMovieDetail.toMovieDetail() = MovieDetail(
    id = this.id,
    backdropPath = this.backdropPath,
    budget = this.budget,
    overview = this.overview,
    popularity = this.popularity,
    posterPath = this.posterPath,
    releaseDate = this.releaseDate,
    runtime = this.runtime,
    tagline = this.tagline,
    title = this.title
)

