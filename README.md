
# Movie Finder Android App
![status](https://github.com/jidasetima/movie-finder-android/actions/workflows/main.yml/badge.svg?branch=main)

This Movie Finder Android app is designed as a sandbox environment for experimenting with the latest Android development features and best practices. It showcases clean architecture, Jetpack Compose, dependency injection with Hilt, and offline support using the Room database. The app fetches movie data from [The Movie Database (TMDb) API](https://api.themoviedb.org/) and allows users to search, browse categories, and view detailed information about each movie.

## Preview 
<p align="center">
  <img src=".github/images/preview_v1.gif" alt="Movie App Preview" width="250">
</p>

## Try the App
You can download APK iterations of the Movie Finder Android App from  [Movie Finder Releases](https://github.com/jidasetima/movie-finder-android/releases) 

## Features
- **Search Movies**: Search for movies by title with live suggestions.
  - Supports pagination with infinite scroll
- **Movie Categories**: Browse and view posters of the latest movies from TMDb's categories:
  - Now Playing
  - Popular
  - Top Rated
  - Upcoming
- **Movie Details**: Select a movie from any category to view detailed information such as the overview, release date, and backdrop image.
- **Offline Support**: Previously fetched movie categories can be viewed even without an internet connection.
- **User Theme Selection**: Choose your preferred theme for the app, with the selection saved across sessions for a personalized experience.
- **Future Enhancements**:
  - More features will be added (TBD).

## Requirements
- **Minimum SDK:** 24 (Android 7.0 Nougat)
- **Target SDK:** 34 (Android Upside Down Cake)

## Libraries and Tools Used

This project leverages the following libraries and tools:

- [**Jetpack Compose**](https://developer.android.com/jetpack/compose) - Android's modern toolkit for building native UI.
- [**Hilt**](https://developer.android.com/training/dependency-injection/hilt-android) - A dependency injection library built on top of Dagger.
- [**Room**](https://developer.android.com/jetpack/androidx/releases/room) - A SQLite object-mapping library for local database storage.
- [**Retrofit**](https://square.github.io/retrofit/) - A type-safe HTTP client for consuming REST APIs.
- [**Moshi**](https://github.com/square/moshi) - A modern JSON library for Android and Java, used for parsing JSON responses.
- [**Coil**](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- [**Kotlin Coroutines**](https://kotlinlang.org/docs/coroutines-overview.html) - Asynchronous programming support for Kotlin.
- [**Navigation Component**](https://developer.android.com/guide/navigation) - Android Jetpack's framework for in-app navigation, integrated with Compose.
- [**Paging 3**](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Library for efficient data pagination in Android apps.
- [**Material Design 3**](https://m3.material.io/) - Implements Material Design 3 (Material You) components in Compose.
- [**JUnit**](https://junit.org/junit4/) - A widely-used testing framework for Java and Kotlin.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/jidasetima/movie-finder-android.git
2. Navigate to https://api.themoviedb.org/ to create an account and generate an API Key
3. Create a copy of the app.properties.example file and rename it to app.properties.
4. Add your API Key to the TMDB_API_KEY field in the app.properties file: ```TMDB_API_KEY=your_api_key_here```


## Contact
John Idasetima - jidasetima@gmail.com