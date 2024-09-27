package com.jogasoft.moviefinder.data.source.network

class NetworkException(
    message: String,
    responseCode: Int,
) : Exception(message)