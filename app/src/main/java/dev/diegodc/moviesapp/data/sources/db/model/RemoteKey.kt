package dev.diegodc.moviesapp.data.sources.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKey(
    @PrimaryKey val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?,
    val type: Int // 1 -> popular 2 -> upcoming
) {
    companion object {
        const val TYPE_POPULAR = 1
        const val TYPE_UPCOMING = 2
    }
}
