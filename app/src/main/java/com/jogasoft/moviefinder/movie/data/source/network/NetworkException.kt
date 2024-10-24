package com.jogasoft.moviefinder.movie.data.source.network

class NetworkException(
    message: String,
    responseCode: Int,
) : Exception(message)