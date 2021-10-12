package dev.diegodc.moviesapp.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.diegodc.moviesapp.domain.model.DetailedMovie
import dev.diegodc.moviesapp.domain.model.SimpleMovie
import dev.diegodc.moviesapp.core.model.Result
import dev.diegodc.moviesapp.core.util.NetworkUtil
import dev.diegodc.moviesapp.data.sources.db.dao.LatestMovieDao
import dev.diegodc.moviesapp.data.sources.db.dao.MovieDao
import dev.diegodc.moviesapp.data.sources.db.dao.RemoteKeyDao
import dev.diegodc.moviesapp.data.sources.db.model.LatestMovieDb
import dev.diegodc.moviesapp.data.sources.db.model.MovieDB
import dev.diegodc.moviesapp.data.sources.remote.MoviesAPI
import dev.diegodc.moviesapp.data.util.networkBoundResource
import dev.diegodc.moviesapp.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MoviesRepository(
    private val moviesAPI: MoviesAPI,
    private val movieDao: MovieDao,
    private val networkUtil: NetworkUtil,
    private val latestMovieDao: LatestMovieDao,
    private val remoteKeyDao: RemoteKeyDao,
) : IMoviesRepository {

    companion object {
        const val FORMAT = "yyyy-MM-dd"
        const val PAGE_SIZE = 20
        const val STARTED_PAGE = 1
    }

    @ExperimentalPagingApi
    override suspend fun loadPopularMovies(): Flow<PagingData<MovieDB>> {
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = MoviesRemoteMediator(
                remoteKeyDao = remoteKeyDao,
                saveMovies = { movies ->
                    movieDao.insertAll(
                        movies.map { result ->
                            val date = try {
                                format.parse(result.release_date ?: "")?.time
                            } catch (e: Exception) {
                                0
                            }
                            MovieDB(
                                id = result.id,
                                title = result.title,
                                adult = result.adult,
                                backdrop_path = result.backdrop_path,
                                original_language = result.original_language ?: "",
                                original_title = result.original_title ?: "",
                                overview = result.overview ?: "",
                                popularity = result.popularity,
                                poster_path = result.poster_path,
                                video = result.video,
                                vote_average = result.vote_average,
                                vote_count = result.vote_count,
                                release_date = date ?: 0,
                                created_at = result.created_at
                            )
                        }
                    )
                },
                remoteLoad = { page ->
                    moviesAPI.getPopularMovies(page = page)
                }
            )
        ) {
            movieDao.getPopularMovies(pageSize = PAGE_SIZE)
        }.flow
    }

    @ExperimentalPagingApi
    override suspend fun loadUpcomingMovies(): Flow<PagingData<MovieDB>> {
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = MoviesRemoteMediator(
                remoteKeyDao = remoteKeyDao,
                saveMovies = { movies ->
                    movieDao.insertAll(
                        movies.map { result ->
                            val date = try {
                                format.parse(result.release_date ?: "")?.time
                            } catch (e: Exception) {
                                0
                            }
                            MovieDB(
                                id = result.id,
                                title = result.title,
                                adult = result.adult,
                                backdrop_path = result.backdrop_path,
                                original_language = result.original_language ?: "",
                                original_title = result.original_title ?: "",
                                overview = result.overview ?: "",
                                popularity = result.popularity,
                                poster_path = result.poster_path,
                                video = result.video,
                                vote_average = result.vote_average,
                                vote_count = result.vote_count,
                                release_date = date ?: 0,
                                created_at = result.created_at
                            )
                        }
                    )
                },
                remoteLoad = { page ->
                    moviesAPI.getUpcomingMovies(page = page)
                }
            )
        ) {
            movieDao.getPopularMovies(pageSize = PAGE_SIZE)
        }.flow
    }


    override suspend fun loadPopularMovies(
        page: Int,
        refresh: Boolean
    ): Flow<Result<List<MovieDB>>> {
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        return networkBoundResource(
            query = {
                Log.d(
                    "PopularMoviesPresenter",
                    "Query Page: $page"
                )
                movieDao.getPopularMovies(page = if (refresh) STARTED_PAGE else page, pageSize = PAGE_SIZE)
            },
            fetch = {
                Log.d(
                    "PopularMoviesPresenter",
                    "Fetch Page: $page"
                )
                moviesAPI.getPopularMovies(page = if (refresh) STARTED_PAGE else page)
            },
            saveFetchResult = {
                Log.d(
                    "PopularMoviesPresenter",
                    "SaveFetchResult Page: $page Response Page: ${it.page}"
                )
                movieDao.insertAll(
                    it.results.map { result ->
                        val date = try {
                            format.parse(result.release_date ?: "")?.time
                        } catch (e: Exception) {
                            0
                        }
                        MovieDB(
                            id = result.id,
                            title = result.title,
                            adult = result.adult,
                            backdrop_path = result.backdrop_path,
                            original_language = result.original_language ?: "",
                            original_title = result.original_title ?: "",
                            overview = result.overview ?: "",
                            popularity = result.popularity,
                            poster_path = result.poster_path,
                            video = result.video,
                            vote_average = result.vote_average,
                            vote_count = result.vote_count,
                            release_date = date ?: 0,
                            created_at = result.created_at
                        )
                    }
                )
            },
            shouldFetch = {
                Log.d(
                    "PopularMoviesPresenter",
                    "shouldFetch Page: $page Response size: ${it.size}"
                )
                val shouldFetch = when {
                    refresh -> true
                    it.isEmpty() -> true
                    else -> false
                }
                networkUtil.isInternetAvailable()
            }
        )
    }

    override suspend fun loadUpcomingMovies(
        page: Int,
        refresh: Boolean
    ): Flow<Result<List<MovieDB>>> {
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        return networkBoundResource(
            query = {
                movieDao.getUpcomingMovies(page = if (refresh) STARTED_PAGE else page, pageSize = PAGE_SIZE)
            },
            fetch = {
                moviesAPI.getUpcomingMovies(page = if (refresh) STARTED_PAGE else page)
            },
            saveFetchResult = {
                movieDao.insertAll(
                    it.results.map { result ->
                        val date = try {
                            format.parse(result.release_date ?: "")?.time
                        } catch (e: Exception) {
                            0
                        }
                        MovieDB(
                            id = result.id,
                            title = result.title,
                            adult = result.adult,
                            backdrop_path = result.backdrop_path,
                            original_language = result.original_language ?: "",
                            original_title = result.original_title ?: "",
                            overview = result.overview ?: "",
                            popularity = result.popularity,
                            poster_path = result.poster_path,
                            video = result.video,
                            vote_average = result.vote_average,
                            vote_count = result.vote_count,
                            release_date = date ?: 0,
                            created_at = result.created_at
                        )
                    }
                )
            },
            shouldFetch = {
                val shouldFetch = when {
                    refresh -> true
                    it.isEmpty() -> true
                    else -> false
                }
                networkUtil.isInternetAvailable()
            }
        )
    }

    override suspend fun getDetails(id: Long): Flow<Result<DetailedMovie>> {
        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
        return networkBoundResource(
            query = {
                flowOf(movieDao.getMovieDetail(id))
                    .map { result ->
                        DetailedMovie(
                            id = result?.id ?: 0,
                            title = result?.title ?: "",
                            overview = result?.overview ?: "",
                            poster = result?.poster_path ?: "",
                            popularity = result?.popularity ?: 0f
                        )
                    }
            },
            fetch = {
                moviesAPI.getMovieDetail(id)
            },
            saveFetchResult = {

                val date = try {
                    format.parse(it.release_date ?: "")?.time
                } catch (e: Exception) {
                    0
                }
                movieDao.insertMovies(
                    it.let { result ->
                        MovieDB(
                            id = result.id,
                            title = result.title,
                            adult = result.adult,
                            backdrop_path = result.backdrop_path,
                            original_language = result.original_language ?: "",
                            original_title = result.original_title ?: "",
                            overview = result.overview ?: "",
                            popularity = result.popularity,
                            poster_path = result.poster_path,
                            video = result.video,
                            vote_average = result.vote_average,
                            vote_count = result.vote_count,
                            release_date = date ?: 0,
                            created_at = result.created_at
                        )
                    }
                )
            },
            shouldFetch = { networkUtil.isInternetAvailable() }
        )
    }

    override suspend fun loadLatestMoviesFlow(): Flow<Result<SimpleMovie>> = networkBoundResource(
        query = {
            flowOf(latestMovieDao.getLatestMovie())
                .map {
                    SimpleMovie(
                        poster = it?.poster_path ?: "",
                        id = it?.movieId ?: 0,
                        title = it?.title ?: "",
                        popularity = it?.popularity ?: 0f,
                        overview = it?.overview ?: ""
                    )
                }
        },
        fetch = {
            moviesAPI.getLatestMovies()
        },
        saveFetchResult = { movie ->
            val date = Date().time
            latestMovieDao.insertMovies(
                movie.apply {
                    created_at = date
                }.let { result ->
                    LatestMovieDb(
                        movieId= result.id,
                        title = result.title,
                        adult = result.adult,
                        backdrop_path = result.backdrop_path,
                        original_language = result.original_language ?: "",
                        original_title = result.original_title ?: "",
                        overview = result.overview ?: "",
                        popularity = result.popularity,
                        poster_path = result.poster_path,
                        video = result.video,
                        vote_average = result.vote_average,
                        vote_count = result.vote_count,
                        release_date = date ?: 0,
                        created_at = result.created_at
                    )
                }
            )
        },
        shouldFetch = { networkUtil.isInternetAvailable() }
    )
}