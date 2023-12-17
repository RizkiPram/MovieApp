package com.example.movieapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "upcoming_movies")
class UpcomingMovieEntity(
    @field:SerializedName("id")
    @PrimaryKey
    val id: Int,
    @field:SerializedName("title")
    val title: String?,
    @field:SerializedName("backdrop_path")
    val backdropPath: String?,
    @field:SerializedName("release_date")
    val releaseDate: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("adult")
    val adult: Boolean
)