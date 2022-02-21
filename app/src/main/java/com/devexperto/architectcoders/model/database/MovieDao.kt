package com.devexperto.architectcoders.model.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun findById(id: Int): Flow<Movie>

    @Query("SELECT COUNT(id) FROM Movie")
    fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Update
    fun updateMovie(movie: Movie)
}