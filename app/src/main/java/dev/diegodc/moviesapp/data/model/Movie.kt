package dev.diegodc.moviesapp.data.model

data class MoviesResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
){
    val isNextPageAvailable : Boolean get() = page < total_pages
}

data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val budget: Int,
    val homepage: String?,
    val id: Long,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Float,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Int,
    val runtime: Int,
    val status: String?,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int
){
    var created_at : Long = 0
}