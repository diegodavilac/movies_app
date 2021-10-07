package dev.diegodc.moviesapp.data.sources.remote

import dev.diegodc.moviesapp.data.model.Movie
import dev.diegodc.moviesapp.data.model.MovieDetails
import dev.diegodc.moviesapp.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") id : Long) : MovieDetails

    @GET("movie/latest")
    suspend fun getLatestMovies() : Movie

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int = 1) : MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int = 1) : MoviesResponse
}