package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.remote.api.ApiConfig
import com.example.movieapp.data.remote.response.MovieResponse
import com.example.movieapp.data.remote.response.ResultsItem
import com.example.movieapp.repository.MovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getNowPlayingMovie() = movieRepository.getNowPlayingMovie()
    fun getListUpcomingMovie() = movieRepository.getUpComingMovie()

    companion object {
        const val TAG = "MainViewModel"
    }
}