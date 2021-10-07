package dev.diegodc.moviesapp.data.repository

import dev.diegodc.moviesapp.domain.model.DetailedMovie
import dev.diegodc.moviesapp.domain.model.SimpleMovie
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import kotlinx.coroutines.delay
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.sources.MoviesDataSource

class MoviesRepository(
    private val remoteDataSource: MoviesDataSource
) : IMoviesRepository {
    override suspend fun loadLatestMovies(): Result<SimpleMovie> {
        return when (val result = remoteDataSource.getLatestMovies()) {
            is Result.Success -> {
                Result.Success(result.data.let {
                    SimpleMovie(
                        poster = it.poster_path?:"",
                        id = it.id,
                        title = it.title,
                        popularity = it.popularity,
                        overview = it.overview
                    )
                })
            }
            is Result.Error -> result
        }
    }

    override suspend fun loadPopularMovies(page: Int): Result<List<SimpleMovie>> {
        return when (val result = remoteDataSource.getPopularMovies(page)) {
            is Result.Success -> {
                Result.Success(result.data.map {
                    SimpleMovie(
                        poster = it.poster_path?:"",
                        id = it.id,
                        title = it.title,
                        popularity = it.popularity,
                        overview = it.overview
                    )
                })
            }
            is Result.Error -> result
        }
    }

    override suspend fun loadUpcomingMovies(page: Int): Result<List<SimpleMovie>> {
        return when (val result = remoteDataSource.getUpcomingMovies(page)) {
            is Result.Success -> {
                Result.Success(result.data.map {
                    SimpleMovie(
                        poster = it.poster_path?:"",
                        id = it.id,
                        title = it.title,
                        popularity = it.popularity,
                        overview = it.overview
                    )
                })
            }
            is Result.Error -> result
        }
    }

    override suspend fun getDetails(id: Long): Result<DetailedMovie> {
        return when(val result = remoteDataSource.getMovieDetail(id)){
            is Result.Success -> {
                Result.Success(
                    DetailedMovie(
                        id = result.data.id,
                        title = result.data.title,
                        overview = result.data.overview,
                        poster = result.data.poster_path?:"",
                        popularity = result.data.popularity
                    )
                )
            }
            is Result.Error -> result
        }
    }
}