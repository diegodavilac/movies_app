package dev.diegodc.moviesapp.data.sources.db

//import dev.diegodc.moviesapp.data.model.Movie
//import dev.diegodc.moviesapp.data.sources.MoviesDataSource
//import dev.diegodc.moviesapp.data.sources.db.dao.MovieDao
//import dev.diegodc.moviesapp.data.sources.db.model.MovieDB
//import java.lang.Exception
//import java.text.SimpleDateFormat
//import java.util.*
//
//class MovieLocalDataSource(
//    private val movieDao: MovieDao
//) : MoviesDataSource {
//    companion object {
//        const val FORMAT = "yyyy-MM-dd"
//    }
//    override suspend fun getPopularMovies(page: Int): List<Movie> {
//        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
//
//
//        return movieDao.getPopularMovies().map { result ->
//            val releaseDate = try {
//                format.format(Date().apply { time = result.release_date })
//            } catch (e: Exception){
//                ""
//            }
//            Movie(
//                id = result.id,
//                title = result.title,
//                adult = result.adult,
//                backdrop_path = result.backdrop_path,
//                budget = 0,
//                homepage = "",
//                imdb_id = "",
//                original_language = result.original_language,
//                original_title = result.original_title,
//                overview = result.overview,
//                popularity = result.popularity,
//                poster_path = result.poster_path,
//                revenue = 0,
//                runtime = 0,
//                status = "",
//                tagline = "",
//                video = result.video,
//                vote_average = result.vote_average,
//                vote_count = result.vote_count,
//                release_date = releaseDate
//            )
//        }
//    }
//
//    override suspend fun getLatestMovies(): Movie? {
//        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
//        val result = movieDao.getLatestMovie()
//        if (result != null) {
//            val releaseDate = try {
//                format.format(Date().apply { time = result.release_date })
//            } catch (e: Exception){
//                ""
//            }
//            return Movie(
//                id = result.id,
//                title = result.title,
//                adult = result.adult,
//                backdrop_path = result.backdrop_path,
//                budget = 0,
//                homepage = "",
//                imdb_id = "",
//                original_language = result.original_language,
//                original_title = result.original_title,
//                overview = result.overview,
//                popularity = result.popularity,
//                poster_path = result.poster_path,
//                revenue = 0,
//                runtime = 0,
//                status = "",
//                tagline = "",
//                video = result.video,
//                vote_average = result.vote_average,
//                vote_count = result.vote_count,
//                release_date = releaseDate
//            )
//        }
//        return null
//    }
//
//    override suspend fun getUpcomingMovies(page: Int): List<Movie> {
//        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
//        return movieDao.getUpcomingMovies().map { result ->
//            val releaseDate = try {
//                format.format(Date().apply { time = result.release_date })
//            } catch (e: Exception){
//                ""
//            }
//            Movie(
//                id = result.id,
//                title = result.title,
//                adult = result.adult,
//                backdrop_path = result.backdrop_path,
//                budget = 0,
//                homepage = "",
//                imdb_id = "",
//                original_language = result.original_language,
//                original_title = result.original_title,
//                overview = result.overview,
//                popularity = result.popularity,
//                poster_path = result.poster_path,
//                revenue = 0,
//                runtime = 0,
//                status = "",
//                tagline = "",
//                video = result.video,
//                vote_average = result.vote_average,
//                vote_count = result.vote_count,
//                release_date = releaseDate
//            )
//        }
//    }
//
//    override suspend fun getMovieDetail(id: Long): Movie? {
//        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
//        val result = movieDao.getMovieDetail(id)
//        if (result != null) {
//            val releaseDate = try {
//                format.format(Date().apply { time = result.release_date })
//            } catch (e: Exception){
//                ""
//            }
//            return Movie(
//                id = result.id,
//                title = result.title,
//                adult = result.adult,
//                backdrop_path = result.backdrop_path,
//                budget = 0,
//                homepage = "",
//                imdb_id = "",
//                original_language = result.original_language,
//                original_title = result.original_title,
//                overview = result.overview,
//                popularity = result.popularity,
//                poster_path = result.poster_path,
//                revenue = 0,
//                runtime = 0,
//                status = "",
//                tagline = "",
//                video = result.video,
//                vote_average = result.vote_average,
//                vote_count = result.vote_count,
//                release_date = releaseDate
//            )
//        }
//        return null
//    }
//
//    override suspend fun saveMovies(movies: List<Movie>) {
//        val format = SimpleDateFormat(FORMAT, Locale.getDefault())
//
//        movieDao.insertAll(
//            movies.map { result ->
//                val date = try {
//                    format.parse(result.release_date?:"")?.time
//                } catch (e: Exception){
//                    0
//                }
//                MovieDB(
//                    id = result.id,
//                    title = result.title,
//                    adult = result.adult,
//                    backdrop_path = result.backdrop_path,
//                    original_language = result.original_language?:"",
//                    original_title = result.original_title?:"",
//                    overview = result.overview?:"",
//                    popularity = result.popularity,
//                    poster_path = result.poster_path,
//                    video = result.video,
//                    vote_average = result.vote_average,
//                    vote_count = result.vote_count,
//                    release_date = date?:0,
//                    created_at = result.created_at
//                )
//            }
//        )
//    }
//}