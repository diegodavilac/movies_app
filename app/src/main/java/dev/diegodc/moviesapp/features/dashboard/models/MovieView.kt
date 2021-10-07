package dev.diegodc.moviesapp.features.dashboard.models

data class MovieView(
    val id: Long,
    val title: String,
    private val image: String,
    val rating: Float,
    val overview: String = ""
) {
    val url: String
        get() = "https://image.tmdb.org/t/p/original${image}"
}
