package dev.diegodc.moviesapp.data.sources.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.diegodc.moviesapp.data.sources.db.dao.MovieDao
import dev.diegodc.moviesapp.data.sources.db.model.MovieDB

@Database(
    version = 1,
    entities = [MovieDB::class]
)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao() : MovieDao
}