package dev.diegodc.moviesapp.domain.model

data class SimpleMovie(
    val poster : String,
    val title: String,
    val id : Long,
    val popularity: Float,
    val overview: String
)
