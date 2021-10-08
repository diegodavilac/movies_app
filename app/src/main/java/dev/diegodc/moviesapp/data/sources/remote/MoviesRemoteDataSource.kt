package dev.diegodc.moviesapp.data.sources.remote

import dev.diegodc.moviesapp.data.model.Movie
import dev.diegodc.moviesapp.data.sources.MoviesDataSource

//class MoviesRemoteDataSource constructor(
//    private val api: MoviesAPI
//) : MoviesDataSource{
//    override suspend fun getPopularMovies(page: Int): List<Movie> {
//        return api.getPopularMovies(page).results
//    }
//
//    override suspend fun getLatestMovies(): Movie {
//        return api.getLatestMovies()
//    }
//
//    override suspend fun getUpcomingMovies(page: Int): List<Movie> {
//        return api.getUpcomingMovies(page).results
//    }
//
//    override suspend fun getMovieDetail(id: Long): Movie {
//        return api.getMovieDetail(id)
//    }
//
//    override suspend fun saveMovies(movies: List<Movie>) {
//
//    }
//}