package dev.diegodc.moviesapp.data.sources

import dev.diegodc.moviesapp.data.model.Movie
import dev.diegodc.moviesapp.data.model.MovieDetails
import dev.diegodc.moviesapp.core.model.Result

interface MoviesDataSource {
    suspend fun getPopularMovies(page: Int) : Result<List<Movie>>
    suspend fun getLatestMovies() : Result<Movie>
    suspend fun getUpcomingMovies(page: Int) : Result<List<Movie>>
    suspend fun getMovieDetail(id: Long) : Result<MovieDetails>
}