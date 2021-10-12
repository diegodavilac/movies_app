package dev.diegodc.moviesapp.data.sources.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.diegodc.moviesapp.data.sources.db.model.LatestMovieDb

@Dao
interface LatestMovieDao {
    @Query("SELECT * FROM latest_movie WHERE id = (SELECT MAX(id) FROM latest_movie)")
    fun getLatestMovie(): LatestMovieDb?

    @Query("SELECT * FROM latest_movie WHERE movieId = :id")
    fun getMovieDetail(id: Long) : LatestMovieDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(vararg movie: LatestMovieDb)

}