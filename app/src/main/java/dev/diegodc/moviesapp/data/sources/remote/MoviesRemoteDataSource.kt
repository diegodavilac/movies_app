package dev.diegodc.moviesapp.data.sources.remote

import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.model.Movie
import dev.diegodc.moviesapp.data.model.MovieDetails
import dev.diegodc.moviesapp.data.sources.MoviesDataSource
import java.lang.Exception

class MoviesRemoteDataSource constructor(
    private val api: MoviesAPI
) : MoviesDataSource{
    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return try {
            val result = api.getPopularMovies(page)

            Result.Success(result.results)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getLatestMovies(): Result<Movie> {
        return try {
            val result = api.getLatestMovies()

            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Result<List<Movie>> {
        return try {
            val result = api.getUpcomingMovies(page)

            Result.Success(result.results)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getMovieDetail(id: Long): Result<MovieDetails> {
        return try {
            val result = api.getMovieDetail(id)

            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}