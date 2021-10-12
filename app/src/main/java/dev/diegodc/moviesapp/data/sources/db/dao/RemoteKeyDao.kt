package dev.diegodc.moviesapp.data.sources.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.diegodc.moviesapp.data.sources.db.model.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remote_key WHERE movieId = :id")
    suspend fun remoteKeysMovieId(id: Long): RemoteKey?

    @Query("SELECT * FROM remote_key WHERE movieId = :id AND type = :type")
    suspend fun remoteKeysMovieId(id: Long, type : Int): RemoteKey?

    @Query("DELETE FROM remote_key")
    suspend fun deleteAll()
}