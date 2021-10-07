package dev.diegodc.moviesapp.data.sources.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.diegodc.moviesapp.data.sources.db.model.MovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * from movie ORDER BY release_date DESC")
    fun getUpcomingMovies() : Flow<List<MovieDB>>

    @Query("SELECT * from movie ORDER BY popularity DESC")
    fun getPopularMovies() : Flow<List<MovieDB>>

    @Query("SELECT * from movie ORDER BY release_date DESC LIMIT 1")
    fun getLatestMovie(): Flow<MovieDB>

    @Query("SELECT * from movie WHERE id = :id")
    fun getMovieDetail(id: Long) : Flow<MovieDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(vararg movie: MovieDB)
}