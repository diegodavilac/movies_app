package dev.diegodc.moviesapp.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import dev.diegodc.moviesapp.domain.model.DetailedMovie
import dev.diegodc.moviesapp.domain.model.SimpleMovie
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.data.sources.db.model.MovieDB
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {
    suspend fun loadPopularMovies() :  Flow<PagingData<MovieDB>>
    suspend fun loadUpcomingMovies() : Flow<PagingData<MovieDB>>
    suspend fun loadPopularMovies(page: Int, refresh: Boolean) :  Flow<Result<List<MovieDB>>>
    suspend fun loadUpcomingMovies(page: Int, refresh: Boolean) : Flow<Result<List<MovieDB>>>
    suspend fun getDetails(id: Long): Flow<Result<DetailedMovie>>
    suspend fun loadLatestMoviesFlow() : Flow<Result<SimpleMovie>>
}