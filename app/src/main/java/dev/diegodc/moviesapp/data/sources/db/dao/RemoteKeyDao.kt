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
    suspend fun remoteKeysCatId(id: Long): RemoteKey?

    @Query("DELETE FROM remote_key")
    suspend fun deleteAll()
}