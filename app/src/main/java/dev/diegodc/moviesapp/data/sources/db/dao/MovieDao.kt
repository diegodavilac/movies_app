package dev.diegodc.moviesapp.data.sources.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.diegodc.moviesapp.data.sources.db.model.MovieDB
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * from movie ORDER BY release_date DESC LIMIT :pageSize")
    fun getUpcomingMovies(pageSize: Int) : PagingSource<Int, MovieDB>

    @Query("SELECT * from movie ORDER BY popularity DESC LIMIT :pageSize")
    fun getPopularMovies(pageSize: Int) : PagingSource<Int, MovieDB>

    @Query("SELECT * from movie  WHERE created_at != 0  ORDER BY created_at LIMIT 1")
    fun getLatestMovie(): MovieDB?

    @Query("SELECT * from movie WHERE id = :id")
    fun getMovieDetail(id: Long) : MovieDB?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(vararg movie: MovieDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieDB>)
}