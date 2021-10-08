package dev.diegodc.moviesapp.data.sources

import dev.diegodc.moviesapp.data.model.Movie

interface MoviesDataSource {
    suspend fun getPopularMovies(page: Int) :List<Movie>
    suspend fun getLatestMovies() : Movie?
    suspend fun getUpcomingMovies(page: Int) : List<Movie>
    suspend fun getMovieDetail(id: Long) : Movie?
    suspend fun saveMovies(movies: List<Movie>)
}