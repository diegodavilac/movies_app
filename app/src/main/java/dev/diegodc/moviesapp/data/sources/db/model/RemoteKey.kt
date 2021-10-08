package dev.diegodc.moviesapp.data.sources.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKey(
    @PrimaryKey val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
