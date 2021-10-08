package dev.diegodc.moviesapp.data.sources.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieDB(
    val adult: Boolean,
    val backdrop_path: String?,
    @PrimaryKey val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    val release_date: Long,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int,
    val created_at: Long?
)