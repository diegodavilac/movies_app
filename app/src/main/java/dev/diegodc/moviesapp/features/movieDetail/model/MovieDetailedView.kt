package dev.diegodc.moviesapp.features.movieDetail.model

data class MovieDetailedView(
    val title: String,
    val id: Long,
    private val image: String,
    val overview: String
){
    val url: String
        get() = "https://image.tmdb.org/t/p/original${image}"
}
