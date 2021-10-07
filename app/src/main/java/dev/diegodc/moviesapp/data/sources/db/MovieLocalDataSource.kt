package dev.diegodc.moviesapp.data.sources.db

import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.model.Movie
import dev.diegodc.moviesapp.data.model.MovieDetails
import dev.diegodc.moviesapp.data.sources.MoviesDataSource

class MovieLocalDataSource : MoviesDataSource {
    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestMovies(): Result<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getUpcomingMovies(page: Int): Result<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(id: Long): Result<MovieDetails> {
        TODO("Not yet implemented")
    }
}