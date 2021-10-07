package dev.diegodc.moviesapp.data.sources.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.diegodc.moviesapp.data.sources.db.converters.TimestampConverter

@Entity(tableName = "movie")
data class MovieDB(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: Any,
    val budget: Int,
    val homepage: String,
    @PrimaryKey val id: Long,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Float,
    val poster_path: String?,
    @TypeConverters(TimestampConverter::class)
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int,
    val created_at: Long?
)