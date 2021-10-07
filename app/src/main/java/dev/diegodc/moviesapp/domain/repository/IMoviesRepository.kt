package dev.diegodc.moviesapp.domain.repository

import dev.diegodc.moviesapp.domain.model.DetailedMovie
import dev.diegodc.moviesapp.domain.model.SimpleMovie
import dev.diegodc.moviesapp.core.model.Result

interface IMoviesRepository {
    suspend fun loadLatestMovies() : Result<SimpleMovie>
    suspend fun loadPopularMovies(page: Int) : Result<List<SimpleMovie>>
    suspend fun loadUpcomingMovies(page: Int) : Result<List<SimpleMovie>>
    suspend fun getDetails(id: Long): Result<DetailedMovie>
}