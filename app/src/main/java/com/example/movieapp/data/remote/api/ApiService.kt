package com.example.movieapp.data.remote.api

import com.example.movieapp.data.remote.response.DetailResponse
import com.example.movieapp.data.remote.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {

    @GET("now_playing")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZWQyNDg0YjA5Mjc1NzhiMTFmZmE5ZTM1MWY2MDJiNiIsInN1YiI6IjY1N2MxZTZkZWEzOTQ5MDBjNGZmZDg3OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.jXcbb2ifRmtrqcK3O6XCBEQyLY-ZdA_7abQk1n1Seo4")
    fun getNowPlayingMovie(
    ): Call<MovieResponse>

    @GET("upcoming")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZWQyNDg0YjA5Mjc1NzhiMTFmZmE5ZTM1MWY2MDJiNiIsInN1YiI6IjY1N2MxZTZkZWEzOTQ5MDBjNGZmZDg3OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.jXcbb2ifRmtrqcK3O6XCBEQyLY-ZdA_7abQk1n1Seo4")
    fun getUpcomingMovie(
    ): Call<MovieResponse>

    @GET("{movie_id}")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZWQyNDg0YjA5Mjc1NzhiMTFmZmE5ZTM1MWY2MDJiNiIsInN1YiI6IjY1N2MxZTZkZWEzOTQ5MDBjNGZmZDg3OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.jXcbb2ifRmtrqcK3O6XCBEQyLY-ZdA_7abQk1n1Seo4")
    fun getDetailMovie(
        @Path("movie_id") movieId:Int
    ): Call<DetailResponse>
}