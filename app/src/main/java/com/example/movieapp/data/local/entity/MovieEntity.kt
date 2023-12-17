package com.example.movieapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
class MovieEntity(
    @field:SerializedName("id")
    @PrimaryKey
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("poster_path")
    val posterPath: String,
    @field:SerializedName("adult")
    val adult: Boolean,
    @field:SerializedName("vote_average")
    val voteAverage: Float,
    @field:SerializedName("release_date")
    val releaseDate: String,
)
