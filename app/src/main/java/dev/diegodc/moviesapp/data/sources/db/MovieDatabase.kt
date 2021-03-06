package dev.diegodc.moviesapp.data.sources.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.diegodc.moviesapp.data.sources.db.dao.LatestMovieDao
import dev.diegodc.moviesapp.data.sources.db.dao.MovieDao
import dev.diegodc.moviesapp.data.sources.db.dao.RemoteKeyDao
import dev.diegodc.moviesapp.data.sources.db.model.LatestMovieDb
import dev.diegodc.moviesapp.data.sources.db.model.MovieDB
import dev.diegodc.moviesapp.data.sources.db.model.RemoteKey

@Database(
    version = 1,
    entities = [MovieDB::class, LatestMovieDb::class, RemoteKey::class]
)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao() : MovieDao
    abstract fun latestMovieDao() : LatestMovieDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}