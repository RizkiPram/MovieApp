package com.example.movieapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.local.entity.MovieEntity
import com.example.movieapp.data.local.entity.UpcomingMovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(meal: List<MovieEntity>)

    @Query("DELETE FROM movies")
    fun deleteMovie()

    @Query("SELECT * FROM upcoming_movies")
    fun getUpComingMovies(): LiveData<List<UpcomingMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUpComingMovie(meal: List<UpcomingMovieEntity>)

    @Query("DELETE FROM upcoming_movies")
    fun deleteUpComingMovie()
}