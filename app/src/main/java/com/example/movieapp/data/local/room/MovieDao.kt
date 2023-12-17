package com.example.movieapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.data.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(meal: List<MovieEntity>)

    @Query("DELETE FROM movies")
    fun deleteMovie()
}